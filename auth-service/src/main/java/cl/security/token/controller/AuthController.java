package cl.security.token.controller;

import cl.security.token.dto.AuthRequest;
import cl.security.token.dto.AuthResponse;
import cl.security.token.model.Usuario;
import cl.security.token.repository.UsuarioRepository;
import cl.security.token.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.correo(), request.contrasena())
            );

            Usuario usuario = usuarioRepository.findByCorreo(request.correo()).orElseThrow();

            String jwtToken = jwtService.generateToken(usuario, usuario.getRol());

            return ResponseEntity.ok(new AuthResponse(jwtToken, usuario.getRol(), usuario.getCorreo()));

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }


    public record RegisterRequest(String correo, String contrasena, String rol) {}
    public record RecoverRequest(String correo, String nuevaContrasena) {}
 
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByCorreo(request.correo()).isPresent()) {
            return ResponseEntity.badRequest().body("El correo ya está registrado");
        }

        Usuario nuevoUsuario = Usuario.builder()
                .correo(request.correo())
                .contrasena(passwordEncoder.encode(request.contrasena()))
                .rol(request.rol() != null ? request.rol() : "OBSERVADOR")
                .build();

        usuarioRepository.save(nuevoUsuario);

        return ResponseEntity.ok(Map.of("mensaje", "Usuario creado exitosamente")); // <-- JSON
    }

    @PutMapping("/recover")
    public ResponseEntity<?> recoverPassword(@RequestBody RecoverRequest request) {
        Usuario usuario = usuarioRepository.findByCorreo(request.correo())
                .orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Correo no encontrado");
        }

        usuario.setContrasena(passwordEncoder.encode(request.nuevaContrasena()));
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("mensaje", "Contraseña actualizada correctamente")); // <-- JSON
    }
    @DeleteMapping("/delete/{correo}")
    public ResponseEntity<?> deleteUser(@PathVariable String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        usuarioRepository.delete(usuario);
        return ResponseEntity.ok(Map.of("mensaje", "Usuario eliminado correctamente")); // <-- JSON
    }
}