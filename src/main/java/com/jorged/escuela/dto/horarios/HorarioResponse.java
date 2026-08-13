package com.jorged.escuela.dto.horarios;

import com.jorged.escuela.dto.datos.DatosGrupo;

public record HorarioResponse(
        Long id,
        DatosGrupo grupo,
        String horario
) {
}
