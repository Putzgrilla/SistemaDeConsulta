package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.config.JwtUserData;
import com.arthurMariano.SistemaDeConsulta.dto.UsuarioDTO;
import com.arthurMariano.SistemaDeConsulta.exception.NaoAchadoException;
import com.arthurMariano.SistemaDeConsulta.mapper.UsuarioMapper;
import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import com.arthurMariano.SistemaDeConsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioDTO buscarMeusDados(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NaoAchadoException("usuario nao encontrado", "id"));

        return usuarioMapper.usuarioParaUsuarioDTO(usuario);
    }
}