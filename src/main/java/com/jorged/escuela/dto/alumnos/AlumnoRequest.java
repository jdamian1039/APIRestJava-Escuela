package com.jorged.escuela.dto.alumnos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlumnoRequest(
        @NotBlank(message = "Nombre es requerido")
        @Size(min=1, max=50, message = "Nombre debe tener entre 1 y 50 caracteres")
        String nombre,
        @NotBlank(message = "Apellido Paterno es requerido")
        @Size(min=1, max=50, message = "Apellido Paterno debe tener entre 1 y 50 caracteres")
        String apellidoPaterno,
        @NotBlank(message = "Apellido Materno es requerido")
        @Size(min=1, max=50, message = "Apellido Materno debe tener entre 1 y 50 caracteres")
        String apellidoMaterno
) {
}
