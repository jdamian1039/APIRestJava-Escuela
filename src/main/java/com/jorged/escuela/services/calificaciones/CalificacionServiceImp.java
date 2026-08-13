package com.jorged.escuela.services.calificaciones;

import com.jorged.escuela.dto.calificaciones.CalificacionRequest;
import com.jorged.escuela.dto.calificaciones.CalificacionResponse;
import com.jorged.escuela.entities.Calificacion;
import com.jorged.escuela.entities.Inscripcion;
import com.jorged.escuela.mapper.CalificacionMapper;
import com.jorged.escuela.repositories.AlumnoRepository;
import com.jorged.escuela.repositories.CalificacionRepository;
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
public class CalificacionServiceImp implements CalificacionService{
    private final CalificacionRepository calificacionRepository;
    private final CalificacionMapper calificacionMapper;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CalificacionResponse> listar() {
        log.info("Consultando las calificaciones");
        return calificacionRepository.findAll().stream().map(calificacionMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalificacionResponse obtenerPorId(Long id) {
        return calificacionMapper.entidadResponse(obtenerCalificacionOException(id));
    }

    @Override
    public CalificacionResponse registrar(CalificacionRequest request) {
        log.info("Registrar calificacion...");
        if (calificacionRepository.existsByInscripcionId(request.idInscripcion()))
            throw new IllegalArgumentException("Ya existe una calificacion para el alumno inscrito");

        Inscripcion inscripcion = obtenerInscripcionOException(request.idInscripcion());
        Calificacion calificacion = calificacionMapper.requestAEntidad(request, inscripcion);
        log.info("Insertando datos de la calificacion...");
        calificacionRepository.save(calificacion);
        log.info("Calificacion registrada...");
        return calificacionMapper.entidadResponse(calificacion);
    }

    @Override
    public CalificacionResponse actualizar(CalificacionRequest request, Long id) {
        log.info("Actualizando calificacion...");
        if (calificacionRepository.existsByInscripcionIdAndIdNot(request.idInscripcion(), id))
            throw new IllegalArgumentException("Ya existe un registro de calificacion para otro alumno");

        Inscripcion inscripcion = obtenerInscripcionOException(request.idInscripcion());
        Calificacion calificacion = obtenerCalificacionOException(id);
        log.info("Actualizando expediente...");
        calificacion.actualizarCalificacion(inscripcion, request.calificacion(), LocalDate.now());
        log.info("Calificacion actualizada...");
        return calificacionMapper.entidadResponse(calificacion);
    }

    @Override
    public void eliminar(Long id) {
        log.warn("Eliminando calificacion...");
        Calificacion calificacion = obtenerCalificacionOException(id);
        calificacionRepository.delete(calificacion);
        log.warn("Calificacion eliminada...");
    }

    private Inscripcion obtenerInscripcionOException(Long id){
        return ServiceUtils.obtenerEntidadOException(inscripcionRepository, id, Inscripcion.class);
    }

    private Calificacion obtenerCalificacionOException(Long id){
        return ServiceUtils.obtenerEntidadOException(calificacionRepository, id, Calificacion.class);
    }
}
