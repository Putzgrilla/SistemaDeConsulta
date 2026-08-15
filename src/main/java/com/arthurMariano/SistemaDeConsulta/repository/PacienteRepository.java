package com.arthurMariano.SistemaDeConsulta.repository;

import com.arthurMariano.SistemaDeConsulta.model.Paciente;
import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    Optional<Usuario> findByLogin(String Login);

    boolean existsByCpf(String cpf);

}
