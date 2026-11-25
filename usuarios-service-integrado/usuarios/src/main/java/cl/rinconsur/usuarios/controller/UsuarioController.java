package cl.rinconsur.usuarios.controller;

import cl.rinconsur.usuarios.model.Usuario;
import cl.rinconsur.usuarios.service.UsuarioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping("/users")
    public List<Usuario> listarUsuarios() {
        return service.listarUsuarios();
    }

}
