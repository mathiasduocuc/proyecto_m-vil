package cl.security.token.config;

import cl.security.token.model.Usuario;
import cl.security.token.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            // Creamos al Supervisor
            Usuario supervisor = Usuario.builder()
                    .correo("supervisor@rinconsur.cl")
                    .contrasena(passwordEncoder.encode("1234")) // Se encripta la contraseña
                    .rol("SUPERVISOR")
                    .build();

            // Creamos al Observador
            Usuario observador = Usuario.builder()
                    .correo("observador@rinconsur.cl")
                    .contrasena(passwordEncoder.encode("1234"))
                    .rol("OBSERVADOR")
                    .build();

            usuarioRepository.save(supervisor);
            usuarioRepository.save(observador);
            
            System.out.println("¡Datos de prueba insertados en la Base de Datos!");
        }
    }
}