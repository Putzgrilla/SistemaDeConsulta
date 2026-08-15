package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.forms.HorarioForm;
import com.arthurMariano.SistemaDeConsulta.service.HorarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/horario")
public class HorarioController {

    private final HorarioService horarioService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody HorarioForm horarioForm) {

        horarioService.salvar(horarioForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/salvarlist")
    public ResponseEntity<?> SalvarLista(@RequestBody List<HorarioForm> horarioForm) {

        horarioService.salvar(horarioForm);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
