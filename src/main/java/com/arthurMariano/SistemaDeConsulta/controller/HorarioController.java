package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.forms.HorarioForm;
import com.arthurMariano.SistemaDeConsulta.service.HorarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Controller
@RequestMapping("/horario")
public class HorarioController {

    private final HorarioService horarioService;
    @Operation(description = "salva horários de atendimento")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody HorarioForm horarioForm) {

        horarioService.salvar(horarioForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @Operation(description = "salva horários de atendimento em lista")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/salvarlist")
    public ResponseEntity<?> SalvarLista(@RequestBody List<HorarioForm> horarioForm) {

        horarioService.salvar(horarioForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
