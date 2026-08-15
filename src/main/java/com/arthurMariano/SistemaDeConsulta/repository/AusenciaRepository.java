package com.arthurMariano.SistemaDeConsulta.repository;

import com.arthurMariano.SistemaDeConsulta.model.Ausencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {
    List<Ausencia> findByMedicoId(Long id);

}
