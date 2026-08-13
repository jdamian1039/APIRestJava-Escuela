package com.jorged.escuela.services.inscripciones;

import com.jorged.escuela.dto.inscripciones.InscripcionRequest;
import com.jorged.escuela.dto.inscripciones.InscripcionResponse;
import com.jorged.escuela.entities.*;
import com.jorged.escuela.exceptions.EntidadRelacionadaException;
import com.jorged.escuela.mapper.InscripcionMapper;
import com.jorged.escuela.repositories.AlumnoRepository;
import com.jorged.escuela.repositories.CalificacionRepository;
import com.jorged.escuela.repositories.GrupoRepository;
import com.jorged.escuela.repositories.InscripcionRepository;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class InscripcionServiceImp implements InscripcionService{

    private final InscripcionRepository inscripcionRepository;
    private final InscripcionMapper inscripcionMapper;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final CalificacionRepository calificacionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<InscripcionResponse> listar() {
        log.info("Mostrando registros de inscripciones...");
        return inscripcionRepository.findAll().stream().map(inscripcionMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public InscripcionResponse obtenerPorId(Long id) {
        log.info("Consultando inscripcion con clave {}...", id);
        return inscripcionMapper.entidadResponse(obtenerIncripcionOExcepcion(id));
    }

    @Override
    public InscripcionResponse registrar(InscripcionRequest request) {
        log.info("Registrando nuevo proceso de inscripcion...");
        log.info("Validando duplicidades...");
        if (inscripcionRepository.existsByAlumnoIdAndGrupoId(request.idAlumno(), request.idGrupo()))
            throw new IllegalArgumentException("El alumno ya fue inscrito a este curso. Valide su información");
        log.info("Obteniendo información...");
        Alumno alumno = obtenerAlumnoOExcepcion(request.idAlumno());
        Grupo grupo = obtenerGrupoOExcepcion(request.idGrupo());

        Inscripcion inscripcion = inscripcionMapper.requestAEntidad(request, alumno, grupo);
        inscripcionRepository.save(inscripcion);
        log.info("Inscripcion realizada...");
        return inscripcionMapper.entidadResponse(inscripcion);
    }

    @Override
    public InscripcionResponse actualizar(InscripcionRequest request, Long id) {
        log.info("Actualizando proceso de inscripcion...");
        log.info("Validando duplicidades...");
        if (inscripcionRepository.existsByAlumnoIdAndGrupoIdAndIdNot(request.idAlumno(), request.idGrupo(), id))
            throw new IllegalArgumentException("El alumno ya fue inscrito al curso. Valide su información");
        log.info("Obteniendo información...");
        Alumno alumno = obtenerAlumnoOExcepcion(request.idAlumno());
        Grupo grupo = obtenerGrupoOExcepcion(request.idGrupo());

        Inscripcion inscripcion = obtenerIncripcionOExcepcion(id);
        inscripcion.actualizarInscripcion(alumno, grupo, LocalDate.now(), null);
        log.info("Inscripcion modificada...");
        return inscripcionMapper.entidadResponse(inscripcion);
    }

    @Override
    public void eliminar(Long id) {
        log.warn("Borrando inscripcion {}", id);
        if (calificacionRepository.existsByInscripcionId(id))
            throw new EntidadRelacionadaException("El alumno tiene calificaciones asignadas a este curso");
        Inscripcion inscripcion = obtenerIncripcionOExcepcion(id);
        inscripcionRepository.delete(inscripcion);
    }

    private Inscripcion obtenerIncripcionOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }
    private Alumno obtenerAlumnoOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }
    private Grupo obtenerGrupoOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }

}
