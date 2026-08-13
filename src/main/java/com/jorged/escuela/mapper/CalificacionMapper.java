package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.calificaciones.CalificacionRequest;
import com.jorged.escuela.dto.calificaciones.CalificacionResponse;
import com.jorged.escuela.entities.Calificacion;
import com.jorged.escuela.entities.Inscripcion;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@AllArgsConstructor
public class CalificacionMapper implements CommonMapper<CalificacionRequest, CalificacionResponse, Calificacion>{

    private final InscripcionMapper inscripcionMapper;

    @Override
    public Calificacion requestAEntidad(CalificacionRequest request) {
        if (request == null) return null;

        return Calificacion.builder().calificacion(request.calificacion()).fechaRegistro(LocalDate.now()).build();
    }

    public Calificacion requestAEntidad(CalificacionRequest request, Inscripcion inscripcion) {
        if (request == null) return null;
        Calificacion calificacion = requestAEntidad(request);
        calificacion.asignarObjeto(inscripcion);
        return calificacion;
    }

    @Override
    public CalificacionResponse entidadResponse(Calificacion entidad) {
        return new CalificacionResponse(
                entidad.getId(),
                inscripcionMapper.obtenerDatosInscripcion(entidad.getInscripcion()),
                entidad.getCalificacion(),
                entidad.getFechaRegistro().toString()
        );
    }
}
