package cl.rinconsur.registros.service;

import cl.rinconsur.registros.model.Registro;
import cl.rinconsur.registros.repository.RegistroRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class RegistroService {

    private final RegistroRepository repository;

    public RegistroService(RegistroRepository repository) {
        this.repository = repository;
    }

    public Registro guardarRegistro(Registro registro) {

        ZoneId zona = ZoneId.of("America/Santiago");

        registro.setId(null);
        registro.setFecha(LocalDate.now(zona));
        registro.setHora(LocalTime.now(zona).withNano(0));

        return repository.save(registro);
    }

    public List<Registro> listarRegistros() {
        return repository.findAll();
    }

    public Optional<Registro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Registro> buscarPorObservador(String observador) {
        return repository.findByObservador(observador);
    }

    public List<Registro> buscarPorFecha(LocalDate fecha) {
        return repository.findByFecha(fecha);
    }

    public List<Registro> buscarPorAcceso(String acceso) {
        return repository.findByAcceso(acceso);
    }

    public List<Registro> buscarPorObservadorYFecha(
            String observador,
            LocalDate fecha) {

        return repository.findByObservadorAndFecha(
                observador,
                fecha
        );
    }
}