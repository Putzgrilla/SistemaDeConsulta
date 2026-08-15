package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.dto.RegistroResponse;
import com.arthurMariano.SistemaDeConsulta.exception.DadoJaCadastradoException;
import com.arthurMariano.SistemaDeConsulta.forms.RecepcionistaForm;
import com.arthurMariano.SistemaDeConsulta.mapper.RecepcionistaMapper;
import com.arthurMariano.SistemaDeConsulta.model.Recepcionista;
import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import com.arthurMariano.SistemaDeConsulta.repository.RecepcionistaRepository;
import com.arthurMariano.SistemaDeConsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecepcionistaService {
    private final RecepcionistaRepository recepcionistaRepository;
    private final PasswordEncoder codificadorDeSenha;
    private final RecepcionistaMapper recepcionistaMapper;
    private final UsuarioRepository usuarioRepository;

    public RegistroResponse cadastra(RecepcionistaForm form) {
        if (usuarioRepository.existsByLogin(form.login())) {

            throw new DadoJaCadastradoException("Login", "Esse e-mail ja foi cadastrado");
        }
        Recepcionista recepcionista = recepcionistaMapper.formsParaEntidade(form);
        recepcionista.setCargo(Cargo.RECEPCIONISTA);
        recepcionista.setSenha(codificadorDeSenha.encode(recepcionista.getSenha()));
        Recepcionista save = recepcionistaRepository.save(recepcionista);
        return new RegistroResponse(save.getId(), save.getLogin());
    }
}
