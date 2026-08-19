package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.dto.EnderecoDto;
import com.arthurMariano.SistemaDeConsulta.dto.UsuarioDto;
import com.arthurMariano.SistemaDeConsulta.model.Endereco;
import com.arthurMariano.SistemaDeConsulta.model.Medico;
import com.arthurMariano.SistemaDeConsulta.model.Paciente;
import com.arthurMariano.SistemaDeConsulta.model.Recepcionista;
import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubclassMapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UsuarioMapper {

    @SubclassMapping(source = Paciente.class, target = UsuarioDto.class)
    @SubclassMapping(source = Medico.class, target = UsuarioDto.class)
    @SubclassMapping(source = Recepcionista.class, target = UsuarioDto.class)
    UsuarioDto usuarioParaUsuarioDTO(Usuario usuario);


    UsuarioDto pacienteParaUsuarioDTO(Paciente paciente); // usado internamente pelo @SubclassMapping

    @Mapping(target = "especialidade", source = "especialidade.nome")
    UsuarioDto medicoParaUsuarioDTO(Medico medico); // idem

    UsuarioDto recepcionistaParaUsuarioDTO(Recepcionista recepcionista); // idem

    EnderecoDto enderecoParaEnderecoDTO(Endereco endereco);
}