package com.arthurMariano.SistemaDeConsulta.repository;

import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByLogin(String Login);

    boolean existsByLogin(String Login);

}
