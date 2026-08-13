package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.datos.DatosCurso;
import com.jorged.escuela.dto.datos.DatosMaestro;
import com.jorged.escuela.dto.maestros.MaestroRequest;
import com.jorged.escuela.dto.maestros.MaestroResponse;
import com.jorged.escuela.entities.Curso;
import com.jorged.escuela.entities.Grupo;
import com.jorged.escuela.entities.Maestro;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class MaestroMapper implements CommonMapper<MaestroRequest, MaestroResponse, Maestro>{

    private final CursoMapper cursoMapper;

    @Override
    public Maestro requestAEntidad(MaestroRequest request) {
        if (request==null) return null;

        return Maestro.builder().nombre(request.nombre().trim())
                .apellidoPaterno(request.apellidoPaterno().trim())
                .apellidoMaterno(request.apellidoMaterno().trim())
                .email(request.email().toLowerCase().trim())
                .telefono(request.telefono().trim())
                .build();
    }

    @Override
    public MaestroResponse entidadResponse(Maestro entidad) {
        if(entidad==null) return null;

        List<DatosCurso> cursos = entidadADatosCurso(entidad);

        return new MaestroResponse(
                entidad.getId(),
                String.join(" ",
                        entidad.getNombre(),
                        entidad.getApellidoPaterno(),
                        entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono(),
                cursos
        );
    }

    private List<DatosCurso> entidadADatosCurso(Maestro entidad){
        if (entidad == null) return List.of();

        return entidad.getGrupos().stream()
                .map(Grupo::getCurso)
                .map(cursoMapper::entidadADatosCurso)
                .toList();
    }/*
    Hola guapo, besos en el me niegas xoxoxo
    me caes muy bien 7w7
    Eres muy grande y lindo (¬ 3 ¬)
    tqm
    *no homo xd
    */

    public DatosMaestro entidadADatosMaestro(Maestro entidad) {
        if (entidad==null) return null;

        return new DatosMaestro(
                String.join(" ",
                        entidad.getNombre(), entidad.getApellidoPaterno(), entidad.getApellidoMaterno()),
                entidad.getEmail(),
                entidad.getTelefono()
        );

    }

}
