package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.config.ConsultaConfig;
import com.arthurMariano.SistemaDeConsulta.dto.DiaHorarioDisponivel;
import com.arthurMariano.SistemaDeConsulta.exception.DataInvalidaException;
import com.arthurMariano.SistemaDeConsulta.model.Ausencia;
import com.arthurMariano.SistemaDeConsulta.model.Consulta;
import com.arthurMariano.SistemaDeConsulta.model.Horario;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import com.arthurMariano.SistemaDeConsulta.model.enums.Status;
import com.arthurMariano.SistemaDeConsulta.repository.AusenciaRepository;
import com.arthurMariano.SistemaDeConsulta.repository.ConsultaRepository;
import com.arthurMariano.SistemaDeConsulta.repository.HorarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
@ConfigurationProperties(prefix = "consulta")
public class DisponivilidadeService {

    private final HorarioRepository horarioRepository;
    private final AusenciaRepository ausenciaRepository;
    private final ConsultaConfig config;
    private final ConsultaRepository consultaRepository;

    public Boolean disponivel(Medico medico, LocalDateTime data) {

        Long idMedico = medico.getId();
        List<Horario> horarios = horarioRepository.findByMedicoId(idMedico);
        List<Ausencia> ausencia = ausenciaRepository.findByMedicoId(idMedico);

        //valida ausencia
        boolean ausente = ausencia.stream().anyMatch(p -> validarPeriodoAusente(p.getInicio(), p.getFim(), data.toLocalDate()));
        if (ausente) throw new DataInvalidaException("dia invalido");

        //Validar dia da semana
        DayOfWeek diaDaSenamaConulta = data.getDayOfWeek();
        Horario horarioValidos = horarios.stream().filter(p -> p.getDiaSemana() == diaDaSenamaConulta).findFirst().orElseThrow(() -> new DataInvalidaException("dia invalido"));

        //Validar Horario
        ValidarHorarioDaconsulta(data, horarioValidos);

        return true;
    }

    private void ValidarHorarioDaconsulta(LocalDateTime data, Horario horarioValidos) {

        LocalTime horas = data.toLocalTime();
        boolean horavalida = horas.getMinute() % 30 == 0;
        boolean primeiroPeriodo = validarHorario(horarioValidos.getInicio(), horarioValidos.getIntervalo(), horas);
        boolean segundoPeriodo = validarHorario(horarioValidos.getFimIntervalo(), horarioValidos.getFim(), horas);
        if ((!primeiroPeriodo && !segundoPeriodo) || !horavalida) throw new DataInvalidaException("Horario invalido");
    }

    public boolean validarHorario(LocalTime inicio, LocalTime fim, LocalTime horas) {
        return !horas.isBefore(inicio) && !horas.isAfter(fim);
    }

    public boolean validarPeriodoAusente(LocalDate inicio, LocalDate fim, LocalDate data) {
        return !data.isBefore(inicio) && !data.isAfter(fim);
    }

    public List<DiaHorarioDisponivel> diaDisponiveis(Long idMedico) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime fim = agora.plusDays(60);

        // Busca consultas marcadas apenas para o médico específico
        List<Consulta> consultasMarcadas = consultaRepository.findByMedicoIdAndDataBetweenAndStatusNot(idMedico, agora, fim, Status.CANCELADA);
        List<Horario> horarios = horarioRepository.findByMedicoId(idMedico);
        List<Ausencia> ausencias = ausenciaRepository.findByMedicoId(idMedico);

        return LocalDate.now().plusDays(config.minimoDeConsultas())
                .datesUntil(LocalDate.now().plusDays(config.maximoDeConsultas()))
                .filter(p -> ausencias.stream().noneMatch(a -> validarPeriodoAusente(a.getInicio(), a.getFim(), p)))
                .filter(p -> horarios.stream().anyMatch(a -> a.getDiaSemana() == p.getDayOfWeek()))
                .map(p -> new DiaHorarioDisponivel(p, horariosNoDia(p, horarios, consultasMarcadas)))
                .toList();
    }

    public List<LocalTime> gerarHorarios(LocalTime inicio, LocalTime fim) {
        return Stream.iterate(inicio, h -> h.isBefore(fim), h -> h.plusMinutes(30))
                .toList();
    }

    private List<LocalTime> horariosNoDia(LocalDate dia, List<Horario> horarios, List<Consulta> consultasMarcadas) {
        // Extrai os horários já ocupados para o dia específico
        Set<LocalTime> horariosOcupados = consultasMarcadas.stream()
                .filter(c -> c.getData().toLocalDate().equals(dia))
                .map(c -> c.getData().toLocalTime())
                .collect(Collectors.toSet());

        // Gera todos os horários disponíveis no dia e remove os que já têm consulta
        return horarios.stream()
                .filter(h -> h.getDiaSemana() == dia.getDayOfWeek())
                .flatMap(h -> Stream.concat(
                        gerarHorarios(h.getInicio(), h.getIntervalo()).stream(),
                        gerarHorarios(h.getFimIntervalo(), h.getFim()).stream()))
                .filter(horario -> !horariosOcupados.contains(horario))
                .toList();
    }
}