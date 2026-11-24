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
        // Retorna todos los usuarios de la base de datos
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

        return ResponseEntity.ok("Usuario creado exitosamente");
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

        return ResponseEntity.ok("Contraseña actualizada correctamente");
    }
    @DeleteMapping("/delete/{correo}")
    public ResponseEntity<?> deleteUser(@PathVariable String correo) {
        // Buscamos al usuario en la base de datos
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado");
        }

        // Lo eliminamos de MySQL
        usuarioRepository.delete(usuario);

        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}