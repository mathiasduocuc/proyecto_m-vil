package cl.security.token.repository;

import cl.security.token.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    // Spring Boot crea la consulta SQL automáticamente para buscar por correo
    Optional<Usuario> findByCorreo(String correo);
}