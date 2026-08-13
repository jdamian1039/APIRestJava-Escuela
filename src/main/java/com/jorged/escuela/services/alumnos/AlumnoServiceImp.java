package com.jorged.escuela.services.alumnos;

import com.jorged.escuela.dto.alumnos.AlumnoRequest;
import com.jorged.escuela.dto.alumnos.AlumnoResponse;
import com.jorged.escuela.entities.Alumno;
import com.jorged.escuela.exceptions.EntidadRelacionadaException;
import com.jorged.escuela.mapper.AlumnoMapper;
import com.jorged.escuela.repositories.AlumnoRepository;
import com.jorged.escuela.repositories.InscripcionRepository;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class AlumnoServiceImp implements AlumnoService {

    private final AlumnoMapper alumnoMapper;
    private final AlumnoRepository alumnoRepository;
    private final InscripcionRepository inscripcionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AlumnoResponse> listar() {
        log.info("Obtieniendo alumnos...");
        return alumnoRepository.findAll()
                .stream().map(alumnoMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AlumnoResponse obtenerPorId(Long id) {
        return alumnoMapper.entidadResponse(generaAlumnoOExcepcion(id));
    }

    @Override
    public AlumnoResponse registrar(AlumnoRequest request) {
        log.info("Registrar nuevo alumno...");

        Alumno alumno = alumnoMapper.requestAEntidad(request, generarEmail(request), generarMatricula(request));
        alumnoRepository.save(alumno);

        log.info("Alumno {} {} {} registrado correctamente", alumno.getNombre(),
                alumno.getApellidoPaterno(), alumno.getApellidoMaterno());
        return alumnoMapper.entidadResponse(alumno);
    }

    @Override
    public AlumnoResponse actualizar(AlumnoRequest request, Long id) {

        Alumno alumno = generaAlumnoOExcepcion(id);
        if (alumno.cambioEnDatos(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno())){

            alumno.actualizarAlumno(request.nombre(), request.apellidoPaterno(), request.apellidoMaterno(),
                    generarEmail(request), generarMatricula(request));

            log.info("Campos actualizados para alumno {} {} {}", request.nombre(),
                    request.apellidoPaterno(), request.apellidoMaterno());
        }

        return alumnoMapper.entidadResponse(alumno);
    }

    @Override
    public void eliminar(Long id) {
        Alumno alumno = generaAlumnoOExcepcion(id);

        log.info("Eliminando alumno con id: {}", id);

        if (inscripcionRepository.existsByAlumnoId(id))
            throw new EntidadRelacionadaException("El alumno tiene inscripciones asigndas,no se puede borrar");

        alumnoRepository.delete(alumno);

        log.info("Alumno con id {} eliminado", id);
    }

    private String generarMatricula(AlumnoRequest req){
        log.info("Generar matrícula");
        return alumnoRepository.generarMatricula(req.nombre().trim(),
                req.apellidoPaterno().trim(), req.apellidoMaterno()).trim();
    }
    private String generarEmail(AlumnoRequest req){
        log.info("Generar matrícula");
        return alumnoRepository.generarEmail(req.nombre().trim(),
                req.apellidoPaterno().trim(), req.apellidoMaterno()).trim();
    }

    private Alumno generaAlumnoOExcepcion(Long id){

        return ServiceUtils.obtenerEntidadOException(alumnoRepository, id, Alumno.class);
    }
}
