package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.dto.LoginResponse;
import com.arthurMariano.SistemaDeConsulta.dto.RegistroResponse;
import com.arthurMariano.SistemaDeConsulta.forms.LoginForm;
import com.arthurMariano.SistemaDeConsulta.forms.MedicoForm;
import com.arthurMariano.SistemaDeConsulta.forms.PacienteForm;
import com.arthurMariano.SistemaDeConsulta.forms.RecepcionistaForm;
import com.arthurMariano.SistemaDeConsulta.service.AuthService;
import com.arthurMariano.SistemaDeConsulta.service.MedicoService;
import com.arthurMariano.SistemaDeConsulta.service.PacienteService;
import com.arthurMariano.SistemaDeConsulta.service.RecepcionistaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Controller()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final PacienteService pacienteService;
    private final MedicoService medicoService;
    private final RecepcionistaService recepcionistaService;
    @Operation(description = "Login de usuario ")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginForm loginForm) {

        LoginResponse Response = authService.Login(loginForm);
        return ResponseEntity.status(HttpStatus.OK).body(Response);


    }
    @Operation(description = "cadastro de paciente ")
    @PostMapping("/register/paciente")
    public ResponseEntity<RegistroResponse> registarPaciente(@Valid @RequestBody PacienteForm paciente) {
        RegistroResponse registroResponse = pacienteService.CadastroPaciente(paciente);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroResponse);
    }
    @Operation(description = "cadastro de medico ")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/register/medico")
    public ResponseEntity<RegistroResponse> registarMedico(@Valid @RequestBody MedicoForm medicoForm) {
        RegistroResponse registroResponse = medicoService.cadastrarMedico(medicoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(registroResponse);
    }
    @Operation(description = "cadastro de recepcionista ")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/register/recepcionista")
    public ResponseEntity<RegistroResponse> registrarRecepcionista(@Valid @RequestBody RecepcionistaForm form) {
        RegistroResponse response = recepcionistaService.cadastra(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
