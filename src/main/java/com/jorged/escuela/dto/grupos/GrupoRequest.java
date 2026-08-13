package com.jorged.escuela.dto.grupos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record GrupoRequest(
        @NotNull(message = "idCurso es necesario")
        @Positive(message = "idCurso siempre debe ser un valor positivo")
        Long idCurso,
        @NotNull(message = "idMestro es necesario")
        @Positive(message = "idMaestro siempre debe ser un valor positivo")
        Long idMaestro,
        @NotNull(message = "idAula es necesario")
        @Positive(message = "idAula siempre debe ser un valor positivo")
        Long idAula,
        @NotBlank(message = "periodo es necesario")
        @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])$", message = "El periodo debe tener el formato YYYY-MM")
        String periodo
) {
}
