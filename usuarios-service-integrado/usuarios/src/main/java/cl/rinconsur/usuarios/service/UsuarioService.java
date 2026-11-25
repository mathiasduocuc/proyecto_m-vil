package cl.rinconsur.usuarios.service;

import cl.rinconsur.usuarios.model.Usuario;
import cl.rinconsur.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    public List<Usuario> listarUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> iniciarSesion(
            String correo, String contrasena) {

        return repository.findByCorreo(correo)
                .filter(usuario ->
                    usuario.getContrasena().equals(contrasena));
    }
}