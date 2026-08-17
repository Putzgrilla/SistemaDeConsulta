package com.arthurMariano.SistemaDeConsulta.controller;

import com.arthurMariano.SistemaDeConsulta.annotations.AcessoGeral;
import com.arthurMariano.SistemaDeConsulta.dto.MedicoPesquisaDto;
import com.arthurMariano.SistemaDeConsulta.service.MedicoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("medico/")
public class MedicoController {

    private final MedicoService medicoService;
    @Operation(description = "pesquisa medicos pela sua especialidade")
    @AcessoGeral
    @GetMapping("especialidade/{id}")
    public ResponseEntity<List<MedicoPesquisaDto>> PesquisarPorEspecialidade(@PathVariable long id) {
        List<MedicoPesquisaDto> medicoPesquisaDto = medicoService.procurarPorEspecialidade(id);
        return ResponseEntity.ok().body(medicoPesquisaDto);
    }
    @Operation(description = "Pesquisa medico pelo seu nome")
    @AcessoGeral
    @GetMapping("nome/{nome}")
    public ResponseEntity<List<MedicoPesquisaDto>> PesquisarPornome(@PathVariable String nome) {
        List<MedicoPesquisaDto> dtos = medicoService.pesquisarMedicoPorNome(nome);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);

    }

}
