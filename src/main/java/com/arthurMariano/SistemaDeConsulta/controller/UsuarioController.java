package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.annotations.AcessoGeral;
import com.arthurMariano.SistemaDeConsulta.config.JwtUserData;
import com.arthurMariano.SistemaDeConsulta.dto.UsuarioDto;
import com.arthurMariano.SistemaDeConsulta.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @AcessoGeral
    @GetMapping("/meusDados")
    @Operation(description = "Ver seus próprios dados")
    public ResponseEntity<UsuarioDto> meusDados(@AuthenticationPrincipal JwtUserData userData) {
        UsuarioDto dto = usuarioService.buscarMeusDados(userData.id());
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    @Operation(description = "ver dados de outro usuario")
    public ResponseEntity<UsuarioDto> meusDados(@PathVariable Long id) {
        UsuarioDto dto = usuarioService.buscarMeusDados(id);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}