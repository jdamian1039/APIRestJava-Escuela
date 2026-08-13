package com.jorged.escuela.dto.horarios;

import jakarta.validation.constraints.*;

public record HorarioRequest(
        @NotNull(message = "idGrupo es necesario")
        @Positive(message = "idCurso siempre debe ser un valor positivo")
        Long idGrupo,
        @NotBlank(message = "dia es necesario")
        String dia,
        @NotBlank(message = "horaInicio es necesario")
        @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Hora de inicio debe escribirse bajo formato HH:mm")
        String horaInicio,
        @NotBlank(message = "horaFin es necesario")
        @Pattern(regexp = "^(?:[01]\\d|2[0-3]):[0-5]\\d$", message = "Hora de término debe escribirse bajo formato HH:mm")
        String horaFin
) {
}
