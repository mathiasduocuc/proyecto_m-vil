package cl.rinconsur.registros.repository;

import cl.rinconsur.registros.model.Registro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroRepository extends JpaRepository<Registro, Long> {

    List<Registro> findByObservador(String observador);

    List<Registro> findByFecha(LocalDate fecha);

    List<Registro> findByAcceso(String acceso);

    List<Registro> findByObservadorAndFecha(
        String observador,
        LocalDate fecha
    );
}