package com.jorged.escuela.dto.grupos;

import com.jorged.escuela.dto.datos.DatosAula;
import com.jorged.escuela.dto.datos.DatosCurso;
import com.jorged.escuela.dto.datos.DatosMaestro;
import com.jorged.escuela.entities.Horario;

import java.util.List;

public record GrupoResponse(
        Long id,
        DatosCurso curso,
        DatosMaestro maestro,
        DatosAula aula,
        List<String> horarios,
        String periodo
) {
}
