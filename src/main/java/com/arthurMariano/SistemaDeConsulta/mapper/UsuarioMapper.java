package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.dto.EnderecoDTO;
import com.arthurMariano.SistemaDeConsulta.dto.UsuarioDTO;
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

    @SubclassMapping(source = Paciente.class, target = UsuarioDTO.class)
    @SubclassMapping(source = Medico.class, target = UsuarioDTO.class)
    @SubclassMapping(source = Recepcionista.class, target = UsuarioDTO.class)
    UsuarioDTO usuarioParaUsuarioDTO(Usuario usuario);


    UsuarioDTO pacienteParaUsuarioDTO(Paciente paciente); // usado internamente pelo @SubclassMapping

    @Mapping(target = "especialidade", source = "especialidade.nome")
    UsuarioDTO medicoParaUsuarioDTO(Medico medico); // idem

    UsuarioDTO recepcionistaParaUsuarioDTO(Recepcionista recepcionista); // idem

    EnderecoDTO enderecoParaEnderecoDTO(Endereco endereco);
}