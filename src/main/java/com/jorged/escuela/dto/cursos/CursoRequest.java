package com.jorged.escuela.dto.cursos;

import jakarta.validation.constraints.*;

public record CursoRequest(
        @NotBlank(message = "Nombre requerido")
        @Size(min = 5, max = 100, message = "Nombre debe tener entre 5 y 100 caracteres")
        String nombre,
        @Size(max = 200, message = "Descripción debe tener máximo 200 caracteres")
        String descripcion,
        @NotNull(message = "Nombre requerido")
        @Min(value = 1, message = "Los créditos mínimos son 1")
        @Max(value = 10, message = "Los créditos máximos son 10")
        Integer creditos
) {
}
