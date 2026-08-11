package com.jorged.escuela.dto.maestros;

import jakarta.validation.constraints.*;

public record MaestroRequest(
        @NotBlank(message = "Nombre es requerido")
        @Size(min=1, max=50, message = "Nombre debe tener entre 1 y 50 caracteres")
        String nombre,
        @NotBlank(message = "Apellido es requerido")
        @Size(min=1, max=50, message = "Apellido paterno debe tener entre 1 y 50 caracteres")
        String apellidoPaterno,
        @NotBlank(message = "Apellido es requerido")
        @Size(min=1, max=50, message = "Apellido Materno debe tener entre 1 y 50 caracteres")
        String apellidoMaterno,
        @NotBlank(message = "Email es requerido")
        @Size(min=8, max=100, message = "Email debe tener entre 8 y 100 caracteres")
        @Email(message = "El email debe ser ejemplo@dominio.com")
        String email,
        @NotBlank(message = "Telefono es requerido")
        @Pattern(regexp = "^[0-9]{10}", message = "Telefono debe tener 10 digigtos")
        String telefono
) {
}
