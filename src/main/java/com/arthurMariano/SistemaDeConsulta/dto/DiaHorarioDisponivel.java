package com.arthurMariano.SistemaDeConsulta.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DiaHorarioDisponivel(LocalDate dia, List<LocalTime> hora) {
}
