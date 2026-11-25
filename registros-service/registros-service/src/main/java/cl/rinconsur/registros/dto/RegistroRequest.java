package cl.rinconsur.registros.dto;

import jakarta.validation.constraints.*;

public record RegistroRequest(

    @NotBlank(message = "El acceso es obligatorio")
    @Pattern(regexp = "A[1-4]", message = "Acceso no valido")
    String acceso,

    @NotBlank(message = "La clasificacion es obligatoria")
    @Size(max = 30)
    String clasificacion,

    @NotBlank(message = "El rango etario es obligatorio")
    @Size(max = 30)
    String rangoEtario,

    @NotBlank(message = "El tipo de compania es obligatorio")
    @Size(max = 30)
    String tipoCompania,

    @NotNull(message = "Debe indicar si lleva compras")
    Boolean comprasOtraTienda,

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Correo no valido")
    @Size(max = 150)
    String observador

) {
}