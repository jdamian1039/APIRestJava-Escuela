package com.jorged.escuela.dto.calificaciones;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CalificacionRequest(
        @NotNull(message = "idInscripcion es requerido")
        @Positive(message = "Valor debe ser positivo")
        Long idInscripcion,
        @NotNull(message = "Calificacion es requerida")
        @Positive(message = "Valor debe ser positivo")
        BigDecimal calificacion
) {
}
