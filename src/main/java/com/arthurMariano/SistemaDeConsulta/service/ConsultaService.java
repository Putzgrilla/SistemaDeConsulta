package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.config.ConsultaConfig;
import com.arthurMariano.SistemaDeConsulta.config.JwtUserData;
import com.arthurMariano.SistemaDeConsulta.dto.ConsultaDto;
import com.arthurMariano.SistemaDeConsulta.dto.ConsultaMarcaResponse;
import com.arthurMariano.SistemaDeConsulta.exception.DataInvalidaException;
import com.arthurMariano.SistemaDeConsulta.exception.NaoAchadoException;
import com.arthurMariano.SistemaDeConsulta.forms.ConsultaForm;
import com.arthurMariano.SistemaDeConsulta.mapper.ConsultaMapper;
import com.arthurMariano.SistemaDeConsulta.model.Consulta;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import com.arthurMariano.SistemaDeConsulta.model.Paciente;
import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import com.arthurMariano.SistemaDeConsulta.model.enums.Status;
import com.arthurMariano.SistemaDeConsulta.repository.ConsultaRepository;
import com.arthurMariano.SistemaDeConsulta.repository.MedicoRepository;
import com.arthurMariano.SistemaDeConsulta.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@ConfigurationProperties(prefix = "consulta")

public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final DisponivilidadeService disponivilidadeService;
    private final ConsultaMapper consultaMapper;
    private final ConsultaConfig config;

    public List<ConsultaDto> buscarMinhasConsultas(JwtUserData usuario) {
        if (usuario.cargo() == Cargo.USUARIO) {
            List<Consulta> consultas = consultaRepository.findByPacienteId(usuario.id());
            return consultaMapper.consultaParaConsultaDto(consultas);
        }
        List<Consulta> consultas = consultaRepository.findByMedicoId(usuario.id());
        return consultaMapper.consultaParaConsultaDto(consultas);


    }

    public List<ConsultaDto> BuscarConsultas(Long id) {
        List<Consulta> consultas = consultaRepository.buscarPorMedicoOuPacienteId(id);

        return consultaMapper.consultaParaConsultaDto(consultas);
    }

    public ConsultaMarcaResponse marca(ConsultaForm consultaForm) {
        LocalDateTime data = consultaForm.data();
        verificarConsultaNaMesmaData(data);
        DentroDoPeriodoDeMarca(data);
        Medico medico = medicoRepository.findById(consultaForm.medicoId()).orElseThrow(() -> new NaoAchadoException("medico", "Medico nao Existe"));
        disponivilidadeService.disponivel(medico, data);
        Consulta consulta = new Consulta();
        Paciente paciente = pacienteRepository.findById(consultaForm.pacienteId()).orElseThrow(() -> new NaoAchadoException("paciente", "Paciente nao Existe"));
        consulta.setData(data);
        consulta.setDescricao(consultaForm.descricao());
        consulta.setStatus(Status.AGENDADA);
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        Consulta save = consultaRepository.save(consulta);
        return consultaMapper.consultaParaConsultaMarcaResponse(save);
    }

    private List<ConsultaDto> ConsultaMarcadasNoDia(LocalDate dia) {
        LocalDateTime inicio = dia.atStartOfDay();
        LocalDateTime fim = dia.atTime(LocalTime.MAX);
        List<Consulta> lista = consultaRepository.findByDataBetween(inicio, fim);
        return lista.stream().map(consultaMapper::consultaParaConsultaDto).toList();

    }

    private void verificarConsultaNaMesmaData(LocalDateTime data) {
        if (consultaRepository.existsByData(data)) throw new DataInvalidaException("Existe uma consulta nessa data ja");
    }

    private void DentroDoPeriodoDeMarca(LocalDateTime data) {
        if (data.isBefore(LocalDateTime.now().plusDays(config.minimoDeConsultas()))) {
            throw new DataInvalidaException("Data da consulta muito proxima");
        }
        if (data.isAfter(LocalDateTime.now().plusDays(config.maximoDeConsultas()))) {
            throw new DataInvalidaException("Data da consulta muito distante");
        }
    }

    public void confirmaConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new NaoAchadoException("consulta nao existe", "id"));
        consulta.setStatus(Status.CONFIRMADA);
        consultaRepository.save(consulta);

    }

    public void cancelarConsulta(Long id) {
        Consulta consulta = consultaRepository.findById(id).orElseThrow(() -> new NaoAchadoException("consulta nao existe", "id"));
        consulta.setStatus(Status.CANCELADA);
        consultaRepository.save(consulta);
    }


}
