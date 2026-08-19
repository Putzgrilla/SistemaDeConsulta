package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.dto.PacienteDto;
import com.arthurMariano.SistemaDeConsulta.dto.RegistroResponse;
import com.arthurMariano.SistemaDeConsulta.exception.DadoJaCadastradoException;
import com.arthurMariano.SistemaDeConsulta.exception.NaoAchadoException;
import com.arthurMariano.SistemaDeConsulta.forms.PacienteForm;
import com.arthurMariano.SistemaDeConsulta.mapper.PacienteMapper;
import com.arthurMariano.SistemaDeConsulta.model.Paciente;
import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import com.arthurMariano.SistemaDeConsulta.repository.PacienteRepository;
import com.arthurMariano.SistemaDeConsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder codificadorDeSenha;
    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;

    public RegistroResponse cadastroPaciente(PacienteForm form) {

        if (usuarioRepository.existsByLogin(form.login())) {

            throw new DadoJaCadastradoException("Login", "Esse e-mail ja foi cadastrado");
        }
        if (pacienteRepository.existsByCpf(form.cpf())) {
            throw new DadoJaCadastradoException("cpf", "Esse CPF ja foi cadastrado");
        }

        Paciente paciente = pacienteMapper.pacineteFormParaPaciente(form);
        paciente.setCargo(Cargo.USUARIO);
        paciente.setSenha(codificadorDeSenha.encode(paciente.getSenha()));
        Paciente save = usuarioRepository.save(paciente);
        return new RegistroResponse(save.getId(), save.getLogin());
    }

    public PacienteDto pesquisarPorcpf(String cpf) {
        Paciente paciente = pacienteRepository.findByCpf(cpf).orElseThrow(() -> new NaoAchadoException("Pacinete nao existe", "cpf"));
        return pacienteMapper.pacienteParaDTO(paciente);
    }
}
