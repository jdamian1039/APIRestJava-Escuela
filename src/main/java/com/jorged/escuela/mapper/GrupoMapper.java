package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.datos.*;
import com.jorged.escuela.dto.grupos.GrupoRequest;
import com.jorged.escuela.dto.grupos.GrupoResponse;
import com.jorged.escuela.entities.*;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class GrupoMapper implements  CommonMapper<GrupoRequest, GrupoResponse, Grupo> {

    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulaMapper aulaMapper;

    @Override
    public Grupo requestAEntidad(GrupoRequest request) {
        if (request==null) return null;

        return Grupo.builder().periodo(request.periodo()).build();
    }

    public Grupo requestAEntidad(GrupoRequest request, Curso curso, Maestro maestro, Aula aula) {
        if (request==null || curso == null || maestro == null || aula == null) return null;

        Grupo grupo = requestAEntidad(request);

        grupo.actualizarDatosAnidados(curso, aula, maestro);

        return grupo;
    }

    @Override
    public GrupoResponse entidadResponse(Grupo entidad) {
        if (entidad == null) return null;

        List<String> horarios = entidadAHorarios(entidad);

        return new GrupoResponse(
                entidad.getId(),
                entidadADatosCurso(entidad),
                entidadADatosMaestro(entidad),
                entidadADatosAula(entidad),
                horarios,
                entidad.getPeriodo()
        );
    }

    private List<String> entidadAHorarios(Grupo entidad){
        if (entidad == null) return List.of();

        return entidad.getHorarios().stream()
                .map(horario ->
                        String.valueOf(horario.getDia().getDescripcion()
                                + " " + horario.getHoraInicio()
                                + " - " + horario.getHoraFin()))
                .toList();
    }

    public DatosGrupo entidadADatosGrupo(Grupo grupo){
        if (grupo == null) return null;

        String nombreCompleto = String.join(" ", grupo.getMaestro().getNombre(),
                grupo.getMaestro().getApellidoPaterno(), grupo.getMaestro().getApellidoMaterno());

        return new DatosGrupo(
                grupo.getCurso().getNombre(),
                grupo.getAula().getNombre(),
                nombreCompleto,
                grupo.getPeriodo()
        );
    }

    private DatosCurso entidadADatosCurso(Grupo entidad){
        return cursoMapper.entidadADatosCurso(entidad.getCurso());
    }
    private DatosMaestro entidadADatosMaestro(Grupo entidad){
        return maestroMapper.entidadADatosMaestro(entidad.getMaestro());
    }
    private DatosAula entidadADatosAula(Grupo entidad){
        return  aulaMapper.entidadADatosAula(entidad.getAula());
    }
}
