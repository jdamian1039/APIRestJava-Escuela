package com.jorged.escuela.dto.inscripciones;

import com.jorged.escuela.dto.datos.DatosAlumno;
import com.jorged.escuela.dto.datos.DatosGrupo;
import com.jorged.escuela.entities.Alumno;
import com.jorged.escuela.entities.Grupo;

import java.math.BigDecimal;

public record InscripcionResponse(
        Long id,
        DatosAlumno alumno,
        DatosGrupo grupo,
        BigDecimal calificacion,
        String fechaInscripcion

) {
}
