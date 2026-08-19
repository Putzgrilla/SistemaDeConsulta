package com.arthurMariano.SistemaDeConsulta.service;

import com.arthurMariano.SistemaDeConsulta.config.TokenConfig;
import com.arthurMariano.SistemaDeConsulta.dto.LoginResponse;
import com.arthurMariano.SistemaDeConsulta.forms.LoginForm;
import com.arthurMariano.SistemaDeConsulta.mapper.PacienteMapper;
import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import com.arthurMariano.SistemaDeConsulta.repository.PacienteRepository;
import com.arthurMariano.SistemaDeConsulta.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder codificadorDeSenha;
    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;
    private final PacienteMapper pacienteMapper;
    private final PacienteRepository pacienteRepository;

    public LoginResponse login(LoginForm login) {
        UsernamePasswordAuthenticationToken userpass = new UsernamePasswordAuthenticationToken(login.login(), login.senha());
        Authentication authentication = authenticationManager.authenticate(userpass);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        assert usuario != null;
        String token = tokenConfig.geradorToken(usuario);
        return new LoginResponse(token);

    }


}
