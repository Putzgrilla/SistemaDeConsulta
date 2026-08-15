package com.arthurMariano.SistemaDeConsulta.repository;

import com.arthurMariano.SistemaDeConsulta.model.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioRepository extends JpaRepository<Horario, Long> {
    List<Horario> findByMedicoId(Long id);
}
