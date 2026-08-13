package com.jorged.escuela.services.cursos;

import com.jorged.escuela.dto.cursos.CursoRequest;
import com.jorged.escuela.dto.cursos.CursoResponse;
import com.jorged.escuela.entities.Curso;
import com.jorged.escuela.exceptions.EntidadRelacionadaException;
import com.jorged.escuela.mapper.CursoMapper;
import com.jorged.escuela.repositories.CursoRepository;
import com.jorged.escuela.repositories.GrupoRepository;
import com.jorged.escuela.utils.ServiceUtils;
import com.jorged.escuela.utils.StringCustomUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class CursoServiceImp implements CursoService{

    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CursoResponse> listar() {
        log.info("Obteniendo todos los cursos...");

        return cursoRepository.findAll().stream().map(cursoMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CursoResponse obtenerPorId(Long id) {
        log.info("Buscando el curso con id {}...", id);
        return cursoMapper.entidadResponse(generarCursoOExcepcion(id));
    }

    @Override
    public CursoResponse registrar(CursoRequest request) {
        log.info("Añadiendo un nuevo curso...");
        Curso curso = cursoMapper.requestAEntidad(request);
        if (validarNombreCurso(request.nombre()))
            throw new IllegalArgumentException("Ya existe un curso con el nombre " + request.nombre());

        cursoRepository.save(curso);
        log.info("Curso añadido...");

        return cursoMapper.entidadResponse(curso);
    }


    @Override
    public CursoResponse actualizar(CursoRequest request, Long id) {
        log.info("Actualizando un curso...");
        Curso curso = generarCursoOExcepcion(id);
        if (validarNombreCurso(request.nombre(), id))
            throw new IllegalArgumentException("Ya existe un curso con el nombre " + request.nombre());

        curso.actualizar(request.nombre(), request.descripcion(), request.creditos());
        log.info("Curso añadido...");

        return cursoMapper.entidadResponse(curso);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Borrando un curso...");
        Curso curso = generarCursoOExcepcion(id);
        if (grupoRepository.existsByCursoId(id))
            throw new EntidadRelacionadaException("No se puede borrar el curso. Tiene grupos disponibles");

        cursoRepository.delete(curso);
    }

    private Curso generarCursoOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }

    private Boolean validarNombreCurso(String nombre){
        return cursoRepository.existsByNombreIgnoreCase(nombre);
    }

    private Boolean validarNombreCurso(String nombre, Long id){
        return cursoRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id);
    }
}
