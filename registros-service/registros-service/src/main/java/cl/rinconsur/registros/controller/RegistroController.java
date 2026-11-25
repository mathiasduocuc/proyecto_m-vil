package cl.rinconsur.registros.controller;

import cl.rinconsur.registros.dto.RegistroRequest;
import cl.rinconsur.registros.model.Registro;
import cl.rinconsur.registros.service.RegistroService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/registros")
public class RegistroController {

    private final RegistroService service;

    public RegistroController(RegistroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Registro> guardar(
            @Valid @RequestBody RegistroRequest request) {

        Registro registro = new Registro();

        registro.setAcceso(request.acceso());
        registro.setClasificacion(request.clasificacion());
        registro.setRangoEtario(request.rangoEtario());
        registro.setTipoCompania(request.tipoCompania());
        registro.setComprasOtraTienda(request.comprasOtraTienda());
        registro.setObservador(request.observador());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.guardarRegistro(registro));
    }

    @GetMapping
    public List<Registro> listar() {
        return service.listarRegistros();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Registro> buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.notFound().build());
    }

    @GetMapping("/observador")
    public List<Registro> buscarPorObservador(
            @RequestParam String correo) {

        return service.buscarPorObservador(correo);
    }

    @GetMapping("/fecha")
    public List<Registro> buscarPorFecha(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return service.buscarPorFecha(fecha);
    }

    @GetMapping("/acceso")
    public List<Registro> buscarPorAcceso(
            @RequestParam String acceso) {

        return service.buscarPorAcceso(acceso);
    }

    @GetMapping("/filtro")
    public List<Registro> filtrar(
            @RequestParam String correo,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fecha) {

        return service.buscarPorObservadorYFecha(
                correo,
                fecha
        );
    }
}