package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.annotations.Funcionario;
import com.arthurMariano.SistemaDeConsulta.dto.PacienteDto;
import com.arthurMariano.SistemaDeConsulta.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paciente")
public class PacienteController {
    private final PacienteService pacienteService;
    @Funcionario
    @Operation(description = "Pesquisa paciente pelo cpf")
    @GetMapping("/pesquisaCpf/{cpf}")
    public ResponseEntity<PacienteDto> pesquisarPacienteCpf(@PathVariable String cpf) {
        PacienteDto pacienteDTO = pacienteService.pesquisarPorcpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(pacienteDTO);

    }

}
