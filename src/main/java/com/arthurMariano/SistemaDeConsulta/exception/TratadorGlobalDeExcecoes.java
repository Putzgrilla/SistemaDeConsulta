package com.arthurMariano.SistemaDeConsulta.exception;

import com.arthurMariano.SistemaDeConsulta.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorGlobalDeExcecoes {
    @ExceptionHandler(NaoAchadoException.class)
    public ResponseEntity<ErroResponse> naoAchado(NaoAchadoException e) {
        ErroResponse erroResponse = new ErroResponse(e.getField(), e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroResponse);

    }

    @ExceptionHandler(LoginInvalidoExecption.class)
    public ResponseEntity<ErroResponse> erroLogin(LoginInvalidoExecption e) {
        ErroResponse erroResponse = new ErroResponse(e.getField(), e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroResponse);
    }

    @ExceptionHandler(DadoJaCadastradoException.class)
    public ResponseEntity<ErroResponse> loginRepetido(DadoJaCadastradoException e) {
        ErroResponse erroResponse = new ErroResponse(e.getField(), e.getMessage(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroResponse>> handleValidation(MethodArgumentNotValidException ex) {
        List<ErroResponse> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> new ErroResponse(
                        e.getField(),
                        e.getDefaultMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now()
                ))
                .toList();

        return ResponseEntity.badRequest().body(erros);


    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponse> handleLoginInvalido(BadCredentialsException e) {
        ErroResponse erroResponse = new ErroResponse("Login", "credenciais erradas", HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erroResponse);
    }

    @ExceptionHandler(DataInvalidaException.class)
    public ResponseEntity<String> dataInvalida(DataInvalidaException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
