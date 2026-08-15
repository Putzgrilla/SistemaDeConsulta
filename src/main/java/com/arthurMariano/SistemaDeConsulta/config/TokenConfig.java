package com.arthurMariano.SistemaDeConsulta.config;

import com.arthurMariano.SistemaDeConsulta.model.Usuario;
import com.arthurMariano.SistemaDeConsulta.model.enums.Cargo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenConfig {
    @Value("${segredo}")
    private String segredo;

    public String geradorToken(Usuario usuario) {
        Algorithm algorithm = Algorithm.HMAC256(segredo);
        return JWT.create().withClaim("userId", usuario.getId()).withClaim("role", usuario.getCargo().name())
                .withSubject(usuario.getLogin())
                .withExpiresAt(Instant.now().plusSeconds(3000))
                .withIssuedAt(Instant.now())
                .sign(algorithm);
    }

    public Optional<JwtUserData> validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);

            DecodedJWT decode = JWT.require(algorithm).build().verify(token);
            return Optional.of(JwtUserData.builder().id(decode.getClaim("userId").asLong()).email(decode.getSubject()).cargo(Cargo.valueOf(decode.getClaim("role").asString())).build());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }

    }
}
