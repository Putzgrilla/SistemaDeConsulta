package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.forms.HorarioForm;
import com.arthurMariano.SistemaDeConsulta.model.Horario;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface HorarioMapper {

    @Mapping(target = "medico", ignore = true)
    Horario horarioFormParaHorario(HorarioForm horarioForm);

    List<Horario> horarioFormParaHorario(List<HorarioForm> horarioForms);
}