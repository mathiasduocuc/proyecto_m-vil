package cl.rinconsur.estadisticas.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroDto(
    Long id,
    String acceso,
    String clasificacion,
    String rangoEtario,
    String tipoCompania,
    boolean comprasOtraTienda,
    LocalDate fecha,
    LocalTime hora,
    String observador
) {
}