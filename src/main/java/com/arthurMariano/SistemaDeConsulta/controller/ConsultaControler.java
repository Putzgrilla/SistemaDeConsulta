package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.annotations.AcessoGeral;
import com.arthurMariano.SistemaDeConsulta.annotations.Funcionario;
import com.arthurMariano.SistemaDeConsulta.config.JwtUserData;
import com.arthurMariano.SistemaDeConsulta.dto.ConsultaDto;
import com.arthurMariano.SistemaDeConsulta.dto.ConsultaMarcaResponse;
import com.arthurMariano.SistemaDeConsulta.dto.DiaHorarioDisponivel;
import com.arthurMariano.SistemaDeConsulta.forms.ConsultaForm;
import com.arthurMariano.SistemaDeConsulta.service.ConsultaService;
import com.arthurMariano.SistemaDeConsulta.service.DisponivilidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller()
@RequestMapping("/consulta")
public class ConsultaControler {
    private final ConsultaService consultaService;
    private final DisponivilidadeService disponivilidadeService;

    @AcessoGeral
    @GetMapping("/minhas")
    public ResponseEntity<List<ConsultaDto>> minhasConsultas(@AuthenticationPrincipal JwtUserData userData) {
        List<ConsultaDto> consultaDtos = consultaService.buscarMinhasConsultas(userData);


        return ResponseEntity.status(HttpStatus.OK).body(consultaDtos);
    }

    @AcessoGeral
    @PostMapping("/marca")
    public ResponseEntity<ConsultaMarcaResponse> marca(@RequestBody ConsultaForm consultaForm) {
        ConsultaMarcaResponse save = consultaService.marca(consultaForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(save);
    }

    @AcessoGeral
    @GetMapping("/horarios/{id}")
    public ResponseEntity<List<DiaHorarioDisponivel>> diasHorarios(@PathVariable Long id) {

        List<DiaHorarioDisponivel> lista = disponivilidadeService.diaDisponiveis(id);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    @Funcionario
    @GetMapping("/confirma/{id}")
    public ResponseEntity<Void> confirmaConsulta(@PathVariable Long id) {
        consultaService.confirmaConsulta(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Funcionario
    @GetMapping("/cancelar/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        consultaService.cancelarConsulta(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @AcessoGeral
    @GetMapping("/cancelarMinhas/{id}")
    public ResponseEntity<Void> cancelarMinhaConsulta(@AuthenticationPrincipal JwtUserData userData, @PathVariable Long id) {
        consultaService.cancelarMinhaConsulta(userData, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/consultas/{id}")
    @Funcionario
    public ResponseEntity<List<ConsultaDto>> pesquisarConsultas(@PathVariable Long id) {
        List<ConsultaDto> consultaDtos = consultaService.BuscarConsultas(id);
        return ResponseEntity.status(HttpStatus.OK).body(consultaDtos);
    }

    @GetMapping("/concluir/{id}")
    @Funcionario
    public ResponseEntity<Void> concluirConsulta(@PathVariable Long id) {
        consultaService.concluirConsulta(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/painel")
    @Funcionario
    public ResponseEntity<List<ConsultaDto>> Painel() {
        List<ConsultaDto> painel = consultaService.painel();
        return ResponseEntity.status(HttpStatus.OK).body(painel);
    }
}
