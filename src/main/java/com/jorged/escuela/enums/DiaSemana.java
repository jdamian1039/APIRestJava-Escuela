package com.jorged.escuela.enums;

import com.jorged.escuela.exceptions.RecursoNoEncontradoException;
import com.jorged.escuela.utils.StringCustomUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DiaSemana {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miercoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sabado");

    private final String descripcion;

    public static DiaSemana obtenerDiaSemanaPorDescripcion(String descripcion){
        StringCustomUtils.validarNoVacio(descripcion, "Descripcion requerida");
        String descripcionNormalizada = StringCustomUtils.quitarAcentos(descripcion);

        for (DiaSemana diaSemana : values()){
            if (StringCustomUtils.quitarAcentos(diaSemana.descripcion).equalsIgnoreCase(descripcionNormalizada)){
                return diaSemana;
            }
        }
        throw  new RecursoNoEncontradoException("No existe ningun dia de la semana llamado: "+ descripcion);
    }
}
