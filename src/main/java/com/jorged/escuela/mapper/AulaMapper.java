package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.aulas.AulaRequest;
import com.jorged.escuela.dto.aulas.AulaResponse;
import com.jorged.escuela.dto.datos.DatosAula;
import com.jorged.escuela.dto.datos.DatosMaestro;
import com.jorged.escuela.entities.Aula;
import com.jorged.escuela.entities.Maestro;
import org.springframework.stereotype.Component;

@Component
public class AulaMapper implements  CommonMapper<AulaRequest, AulaResponse, Aula>{
    @Override
    public Aula requestAEntidad(AulaRequest request) {
        if (request == null) return null;
        return Aula.builder()
                .nombre(request.nombre())
                .capacidad(request.capacidad())
                .build();
    }

    @Override
    public AulaResponse entidadResponse(Aula entidad) {
        if (entidad == null) return null;

        return new AulaResponse(
                entidad.getId(),
                entidad.getNombre().trim(),
                entidad.getCapacidad()
        );

    }

    public DatosAula entidadADatosAula(Aula entidad) {
        if (entidad==null) return null;

        return new DatosAula(
                entidad.getNombre(),
                entidad.getCapacidad());

    }
}
