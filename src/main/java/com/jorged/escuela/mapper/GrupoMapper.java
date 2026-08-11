package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.cursos.GrupoRequest;
import com.jorged.escuela.dto.cursos.GrupoResponse;
import com.jorged.escuela.entities.Grupo;
import org.springframework.stereotype.Component;

@Component
public class GrupoMapper implements  CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        return null;
    }

    @Override
    public GrupoResponse entidadResponse(Grupo entidad) {
        return null;
    }
}
