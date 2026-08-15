package com.arthurMariano.SistemaDeConsulta;

import com.arthurMariano.SistemaDeConsulta.config.ConsultaConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ConsultaConfig.class)
@SpringBootApplication
public class SistemaDeConsultaApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaDeConsultaApplication.class, args);
    }

}
