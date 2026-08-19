package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.dto.PacienteDto;
import com.arthurMariano.SistemaDeConsulta.forms.EnderecoForm;
import com.arthurMariano.SistemaDeConsulta.forms.PacienteForm;
import com.arthurMariano.SistemaDeConsulta.model.Endereco;
import com.arthurMariano.SistemaDeConsulta.model.Paciente;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PacienteMapper {

    @Mapping(target = "login", source = "login")
    @Mapping(target = "nome", source = "nome")
    @Mapping(target = "senha", source = "senha")
    @Mapping(target = "endereco", source = "endereco")
    Paciente pacineteFormParaPaciente(PacienteForm pacienteForm);

    Endereco enderecoFormParaEndereco(EnderecoForm enderecoForm);

    PacienteDto pacienteParaDTO(Paciente paciente);

}