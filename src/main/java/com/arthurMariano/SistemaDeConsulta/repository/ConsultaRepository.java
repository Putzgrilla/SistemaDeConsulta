package com.arthurMariano.SistemaDeConsulta.repository;

import com.arthurMariano.SistemaDeConsulta.model.Consulta;
import com.arthurMariano.SistemaDeConsulta.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    @Query("SELECT c FROM Consulta c WHERE c.medico.id = :id OR c.paciente.id = :id")
    List<Consulta> buscarPorMedicoOuPacienteId(Long id);

    List<Consulta> findByPacienteId(Long id);

    List<Consulta> findByMedicoId(Long id);

    boolean existsByData(LocalDateTime data);

    List<Consulta> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Consulta> findByStatusAndDataBetween(
            Status status,
            LocalDateTime inicio,
            LocalDateTime fim
    );

    List<Consulta> findByMedicoIdAndDataBetweenAndStatusNot(
            Long medicoId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Status statusExcluido
    );
}

