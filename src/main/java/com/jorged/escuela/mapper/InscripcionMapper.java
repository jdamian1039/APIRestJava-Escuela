package com.jorged.escuela.mapper;

import com.jorged.escuela.dto.datos.DatosAlumno;
import com.jorged.escuela.dto.datos.DatosCalificacion;
import com.jorged.escuela.dto.datos.DatosInscripcion;
import com.jorged.escuela.dto.inscripciones.InscripcionRequest;
import com.jorged.escuela.dto.inscripciones.InscripcionResponse;
import com.jorged.escuela.entities.Alumno;
import com.jorged.escuela.entities.Calificacion;
import com.jorged.escuela.entities.Grupo;
import com.jorged.escuela.entities.Inscripcion;
import com.jorged.escuela.repositories.CalificacionRepository;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class InscripcionMapper implements CommonMapper<InscripcionRequest, InscripcionResponse, Inscripcion>{

    private final AlumnoMapper alumnoMapper;
    private final GrupoMapper grupoMapper;
    private final CalificacionRepository calificacionRepository;

    @Override
    public Inscripcion requestAEntidad(InscripcionRequest request) {
        if (request==null) return null;
        return Inscripcion.builder().fechaInscripcion(LocalDate.now()).build();
    }

    public Inscripcion requestAEntidad(InscripcionRequest request, Alumno alumno, Grupo grupo) {
        if (request==null || alumno == null || grupo == null) return null;

        Inscripcion inscripcion = requestAEntidad(request);
        inscripcion.llenarObjetosInscripcion(alumno, grupo, obtenerDatosCalificacion(inscripcion));

        return inscripcion;
    }
    @Override
    public InscripcionResponse entidadResponse(Inscripcion entidad) {
        BigDecimal calificacion = entidad.getCalificacion() == null ?
                null : entidad.getCalificacion().getCalificacion();

        return new InscripcionResponse(
                entidad.getId(),
                obtenerDatosAlumno(entidad),
                grupoMapper.entidadADatosGrupo(entidad.getGrupo()),
                calificacion,
                LocalDate.now().toString()
        );
    }

    private Calificacion obtenerDatosCalificacion(Inscripcion entidad){
        if (entidad==null)
            return null;

        return calificacionRepository.getCalificacionByInscripcionId(entidad.getId());
    }

    private DatosAlumno obtenerDatosAlumno(Inscripcion entidad){
        return alumnoMapper.entidadADatosAlumno(entidad.getAlumno());
    }
    public DatosInscripcion obtenerDatosInscripcion(Inscripcion entidad){
        if (entidad==null)
            return null;
        return new DatosInscripcion(
                alumnoMapper.entidadADatosAlumno(entidad.getAlumno()),
                grupoMapper.entidadADatosGrupo(entidad.getGrupo()),
                entidad.getFechaInscripcion().toString()
        );
    }
}
