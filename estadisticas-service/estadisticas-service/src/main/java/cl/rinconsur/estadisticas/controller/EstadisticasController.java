package cl.rinconsur.estadisticas.controller;

import cl.rinconsur.estadisticas.service.EstadisticasService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    private final EstadisticasService estadisticasService;

    public EstadisticasController(
        EstadisticasService estadisticasService
    ) {
        this.estadisticasService = estadisticasService;
    }

    @GetMapping
    public Map<String, Object> obtenerEstadisticas(
        @AuthenticationPrincipal Jwt jwt
    ) {
        return estadisticasService.obtenerResumen(
            jwt.getTokenValue(),
            null
        );
    }

    @GetMapping("/fecha")
    public Map<String, Object> obtenerPorFecha(
        @AuthenticationPrincipal Jwt jwt,
        @RequestParam("fecha")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate fecha
    ) {
        return estadisticasService.obtenerResumen(
            jwt.getTokenValue(),
            fecha
        );
    }
}