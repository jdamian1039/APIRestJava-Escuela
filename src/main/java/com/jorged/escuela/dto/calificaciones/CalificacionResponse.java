package com.jorged.escuela.dto.calificaciones;

import com.jorged.escuela.dto.datos.DatosInscripcion;

import java.math.BigDecimal;

public record CalificacionResponse(
        Long id,
        DatosInscripcion inscripcion,
        BigDecimal calificacion,
        String fechaRegistro
) {
}
