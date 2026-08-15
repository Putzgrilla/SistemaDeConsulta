package com.arthurMariano.SistemaDeConsulta.forms;

import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record HorarioForm(@NotNull DayOfWeek diaSemana, @NotNull Long medico, @NotNull LocalTime inicio,
                          @NotNull LocalTime fim, @NotNull LocalTime intervalo, @NotNull LocalTime fimIntervalo) {


}
