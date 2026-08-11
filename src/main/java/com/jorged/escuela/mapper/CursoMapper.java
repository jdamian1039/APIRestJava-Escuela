package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.cursos.CursoRequest;
import com.jorged.escuela.dto.cursos.CursoResponse;
import com.jorged.escuela.dto.datos.DatosCurso;
import com.jorged.escuela.entities.Curso;
import org.springframework.stereotype.Component;

@Component
public class CursoMapper implements CommonMapper<CursoRequest, CursoResponse, Curso>{
    @Override
    public Curso requestAEntidad(CursoRequest request) {
        if(request==null) return null;

        String descripcion = request.descripcion() != null ? request.descripcion().trim() : null;

        return Curso.builder().nombre(request.nombre())
                .descripcion(descripcion)
                .creditos(request.creditos())
                .build();
    }

    @Override
    public CursoResponse entidadResponse(Curso entidad) {
        if (entidad==null) return null;

        String descripcion = entidad.getDescripcion() == null ? "Sin descripción" : entidad.getDescripcion();

        return new CursoResponse(
                entidad.getId(),
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );

    }

    public DatosCurso entidadADatosCurso(Curso entidad) {
        if (entidad==null) return null;

        String descripcion = entidad.getDescripcion() == null ? "Sin descripción" : entidad.getDescripcion();

        return new DatosCurso(
                entidad.getNombre(),
                descripcion,
                entidad.getCreditos()
        );

    }
}
