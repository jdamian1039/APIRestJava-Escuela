package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.horarios.HorarioRequest;
import com.jorged.escuela.dto.horarios.HorarioResponse;
import com.jorged.escuela.entities.Grupo;
import com.jorged.escuela.entities.Horario;
import com.jorged.escuela.enums.DiaSemana;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HorarioMapper implements CommonMapper<HorarioRequest, HorarioResponse, Horario>{

    private final GrupoMapper grupoMapper;

    @Override
    public Horario requestAEntidad(HorarioRequest request) {
        if (request == null) return null;

        return Horario.builder().horaInicio(request.horaInicio()).horaFin(request.horaFin()).build();
    }

    public Horario requestAEntidad(HorarioRequest request, DiaSemana dia, Grupo grupo) {
        if (request == null || grupo == null) return null;

        Horario horario = requestAEntidad(request);
        horario.asignarElementosHorario(grupo, dia);

        return horario;
    }

    @Override
    public HorarioResponse entidadResponse(Horario entidad) {
        if (entidad == null) return null;

        return new HorarioResponse(
                entidad.getId(),
                grupoMapper.entidadADatosGrupo(entidad.getGrupo()),
                String.join(" ",
                entidad.getDia().getDescripcion(), entidad.getHoraInicio(), entidad.getHoraFin()
                )
        );
    }
}
