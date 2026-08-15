package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.dto.EspecialidadeResponse;
import com.arthurMariano.SistemaDeConsulta.forms.EspecialidadeForm;
import com.arthurMariano.SistemaDeConsulta.model.Especialidade;
import com.arthurMariano.SistemaDeConsulta.repository.EspecialidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EspecialidadeService {
    private final EspecialidadeRepository especialidadeRepository;


    public EspecialidadeResponse salvar(EspecialidadeForm especialidadeForm) {
        Especialidade especialidade = new Especialidade();
        especialidade.setNome(especialidadeForm.nome());

        Especialidade save = especialidadeRepository.save(especialidade);
        return new EspecialidadeResponse(save.getId(), save.getNome());

    }

    public List<EspecialidadeResponse> buscarTodos() {
        List<Especialidade> todos = especialidadeRepository.findAll();

        return todos.stream().map(p -> new EspecialidadeResponse(p.getId(), p.getNome())).toList();
    }
}
