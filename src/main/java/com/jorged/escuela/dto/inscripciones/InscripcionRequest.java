package com.jorged.escuela.dto.inscripciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record InscripcionRequest(
        @NotNull(message = "idAlumno requerido")
        @Positive(message = "idAlumno debe ser positivo")
        Long idAlumno,
        @NotNull(message = "idGrupo requerido")
        @Positive(message = "idGrupo debe ser positivo")
        Long idGrupo
) {
}
