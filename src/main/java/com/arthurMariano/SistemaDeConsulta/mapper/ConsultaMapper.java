package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.dto.ConsultaDto;
import com.arthurMariano.SistemaDeConsulta.dto.ConsultaMarcaResponse;
import com.arthurMariano.SistemaDeConsulta.model.Consulta;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = {MedicoMapper.class, PacienteMapper.class}, unmappedTargetPolicy = ReportingPolicy.WARN)

public interface ConsultaMapper {

    @Mapping(source = "medico.nome", target = "medico")
    @Mapping(source = "paciente.nome", target = "paciente")
    ConsultaMarcaResponse consultaParaConsultaMarcaResponse(Consulta consulta);

    List<ConsultaMarcaResponse> consultaParaConsultaMarcaResponse(List<Consulta> consultas);


    ConsultaDto consultaParaConsultaDto(Consulta consulta);

    List<ConsultaDto> consultaParaConsultaDto(List<Consulta> consulta);
}