package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.annotations.AcessoGeral;
import com.arthurMariano.SistemaDeConsulta.dto.EspecialidadeResponse;
import com.arthurMariano.SistemaDeConsulta.forms.EspecialidadeForm;
import com.arthurMariano.SistemaDeConsulta.service.EspecialidadeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller()
@RequestMapping("/especialidade")
public class EspecialidadeController {

    private final EspecialidadeService especialidadeServicel;

    @PostMapping("/salva")
    public ResponseEntity<EspecialidadeResponse> salvar(@Valid @RequestBody EspecialidadeForm especialidadeForm) {
        EspecialidadeResponse salvar = especialidadeServicel.salvar(especialidadeForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvar);
    }

    @GetMapping("/buscarTodos")
    @AcessoGeral
    public ResponseEntity<List<EspecialidadeResponse>> buscarTodos() {

        List<EspecialidadeResponse> todas = especialidadeServicel.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(todas);
    }

}
