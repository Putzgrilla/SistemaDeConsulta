package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.dto.MedicoPesquisaDto;
import com.arthurMariano.SistemaDeConsulta.forms.MedicoForm;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MedicoMapper {
    @Mapping(source = "especialidade.nome", target = "especialidade")
    MedicoPesquisaDto medicoParaMedicoPesquisaDTO(Medico medico);

    List<MedicoPesquisaDto> medicoParaMedicoPesquisaDTO(List<Medico> medico);

    @Mapping(target = "especialidade", ignore = true)
    @Mapping(target = "cargo", ignore = true)
    Medico medicoFormParaMedico(MedicoForm medicoForm);

}

