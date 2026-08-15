package com.arthurMariano.SistemaDeConsulta.mapper;

import com.arthurMariano.SistemaDeConsulta.forms.RecepcionistaForm;
import com.arthurMariano.SistemaDeConsulta.model.Recepcionista;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)

public interface RecepcionistaMapper {
    Recepcionista formsParaEntidade(RecepcionistaForm form);
}