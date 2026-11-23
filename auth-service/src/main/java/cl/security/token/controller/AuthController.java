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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // 1. Spring Security verifica si el correo y contraseña son válidos
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.correo(), request.contrasena())
            );

            // 2. Si es válido, buscamos al usuario en la BD
            Usuario usuario = usuarioRepository.findByCorreo(request.correo()).orElseThrow();

            // 3. Generamos el Token JWT
            String jwtToken = jwtService.generateToken(usuario, usuario.getRol());

            // 4. Devolvemos la respuesta JSON a Android
            return ResponseEntity.ok(new AuthResponse(jwtToken, usuario.getRol(), usuario.getCorreo()));

        } catch (Exception e) {
            // Si las credenciales son incorrectas, devolvemos un error 401
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}