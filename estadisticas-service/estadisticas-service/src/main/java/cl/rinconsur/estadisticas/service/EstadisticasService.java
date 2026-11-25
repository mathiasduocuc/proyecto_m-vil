package cl.rinconsur.estadisticas.service;

import cl.rinconsur.estadisticas.dto.RegistroDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {

    private final RestClient restClient;

    public EstadisticasService(
        @Value("${registros.service.url}") String url
    ) {
        this.restClient = RestClient.builder()
            .baseUrl(url)
            .build();
    }

    public List<RegistroDto> obtenerRegistros(String token) {

        try {
            RegistroDto[] registros = restClient.get()
                .uri("/api/registros")
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .body(RegistroDto[].class);

            if (registros == null) {
                throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Respuesta de registros vacia"
                );
            }

            return Arrays.asList(registros);

        } catch (RestClientException e) {
            throw new ResponseStatusException(
                HttpStatus.BAD_GATEWAY,
                "No se pudo consultar registros-service",
                e
            );
        }
    }

    public Map<String, Object> obtenerResumen(
        String token,
        LocalDate fecha
    ) {

        List<RegistroDto> registros = obtenerRegistros(token);

        if (fecha != null) {
            registros = registros.stream()
                .filter(r -> fecha.equals(r.fecha()))
                .toList();
        }

        Map<String, Object> resultado = new LinkedHashMap<>();

        resultado.put("totalClientes", registros.size());

        resultado.put("porAcceso",
            agrupar(registros, RegistroDto::acceso));

        resultado.put("porClasificacion",
            agrupar(registros, RegistroDto::clasificacion));

        resultado.put("porRangoEtario",
            agrupar(registros, RegistroDto::rangoEtario));

        resultado.put("porTipoCompania",
            agrupar(registros, RegistroDto::tipoCompania));

        resultado.put("porFecha",
            agrupar(registros, r -> r.fecha().toString()));

        long conCompras = registros.stream()
            .filter(RegistroDto::comprasOtraTienda)
            .count();

        resultado.put("comprasOtraTienda", conCompras);

        resultado.put("sinComprasOtraTienda",
            registros.size() - conCompras);

        double porcentaje = registros.isEmpty()
            ? 0.0
            : conCompras * 100.0 / registros.size();

        resultado.put("porcentajeComprasOtraTienda",
            Math.round(porcentaje * 100.0) / 100.0);

        return resultado;
    }

    private Map<String, Long> agrupar(
        List<RegistroDto> registros,
        Function<RegistroDto, String> campo
    ) {

        return registros.stream()
            .collect(Collectors.groupingBy(
                campo,
                Collectors.counting()
            ));
    }
}