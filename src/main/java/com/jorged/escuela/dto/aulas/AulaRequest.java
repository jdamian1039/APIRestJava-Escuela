package com.jorged.escuela.dto.aulas;

import jakarta.validation.constraints.*;

public record AulaRequest(
        @NotBlank(message = "Nombre es requerido")
        @Size(min=1, max = 100, message = "Nombre debe tener entre 1 y 100 caracteres")
        String nombre,
        @NotNull(message = "Capacidad es requerida")
        @Max(value = 9999, message = "Capacidad máxima puede ser 9999")
        @Min(value=1, message = "La capacidad minima debe ser 1")
        Integer capacidad
) {
}
