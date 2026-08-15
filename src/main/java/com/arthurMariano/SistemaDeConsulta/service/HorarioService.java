package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.exception.NaoAchadoException;
import com.arthurMariano.SistemaDeConsulta.forms.HorarioForm;
import com.arthurMariano.SistemaDeConsulta.mapper.HorarioMapper;
import com.arthurMariano.SistemaDeConsulta.model.Horario;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import com.arthurMariano.SistemaDeConsulta.repository.HorarioRepository;
import com.arthurMariano.SistemaDeConsulta.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorarioService {
    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;
    private final MedicoRepository medicoRepository;


    public void salvar(HorarioForm horarioForm) {
        Horario horario = horarioMapper.horarioFormParaHorario(horarioForm);
        Medico medico = medicoRepository.findById(horarioForm.medico()).orElseThrow(() -> new NaoAchadoException("Medico não Existe", "Medico"));
        horario.setMedico(medico);
        horario.setAtivo(true);
        horarioRepository.save(horario);

    }

    public void salvar(List<HorarioForm> horarioForms) {
        List<Horario> horarios = horarioForms.stream()
                .map(form -> {
                    Horario horario = horarioMapper.horarioFormParaHorario(form);
                    Medico medico = medicoRepository.findById(form.medico())
                            .orElseThrow(() -> new NaoAchadoException("Medico não Existe", "Medico"));
                    horario.setMedico(medico);
                    horario.setAtivo(true);
                    return horario;
                })
                .toList();

        horarioRepository.saveAll(horarios);
    }

}
