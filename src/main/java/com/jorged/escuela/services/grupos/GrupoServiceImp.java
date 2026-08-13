package com.jorged.escuela.services.grupos;

import com.jorged.escuela.dto.grupos.GrupoRequest;
import com.jorged.escuela.dto.grupos.GrupoResponse;
import com.jorged.escuela.entities.Aula;
import com.jorged.escuela.entities.Curso;
import com.jorged.escuela.entities.Grupo;
import com.jorged.escuela.entities.Maestro;
import com.jorged.escuela.exceptions.EntidadRelacionadaException;
import com.jorged.escuela.mapper.AulaMapper;
import com.jorged.escuela.mapper.CursoMapper;
import com.jorged.escuela.mapper.GrupoMapper;
import com.jorged.escuela.mapper.MaestroMapper;
import com.jorged.escuela.repositories.*;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class GrupoServiceImp implements GrupoService {

    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final CursoRepository cursoRepository;
    private final MaestroRepository maestroRepository;
    private final AulaRepository aulaRepository;
    private final HorarioRepository horarioRepository;
    private final CursoMapper cursoMapper;
    private final MaestroMapper maestroMapper;
    private final AulaMapper aulaMapper;

    @Override
    public List<GrupoResponse> listar() {
        log.info("Listando grupos...");
        return grupoRepository.findAll().stream().map(grupoMapper::entidadResponse).toList();
    }

    @Override
    public GrupoResponse obtenerPorId(Long id) {
        log.info("Listando info de grupo {}...", id);
        return grupoMapper.entidadResponse(obtenerGrupoOExcepcion(id));
    }

    @Override
    public GrupoResponse registrar(GrupoRequest request) {
        log.info("Registrando grupo...");
        if (grupoRepository.consultarDatosUnicos(request.idCurso(), request.idAula(), request.idMaestro(), request.periodo()))
            throw new IllegalArgumentException("Ya existe un grupo programado en la misma aula, con el mismo maestro y del mismo curso");

        log.info("Obteniendo entidad de grupo...");
        Curso curso = obtenerCursoOExcepcion(request.idCurso());
        Aula aula = obtenerAulaOExcepcion(request.idAula());
        Maestro maestro = obtenerMaestroOExcepcion(request.idMaestro());

        Grupo grupo = grupoMapper.requestAEntidad(request, curso, maestro, aula);
        log.info("Guardando grupo...");
        grupoRepository.save(grupo);
        log.info("Grupo registrado...");
        return grupoMapper.entidadResponse(grupo);
    }

    @Override
    public GrupoResponse actualizar(GrupoRequest request, Long id) {
        log.info("Actualizando informacion de grupo...");
        if (grupoRepository.consultarDatosUnicos(request.idCurso(), request.idAula(),
                request.idMaestro(), request.periodo(), id))
            throw new IllegalArgumentException("Ya existe otro registro con la misma información. " +
                    "No se procede con la actualizacion");
        log.info("Obteniendo datos de la entidad...");
        Grupo grupo = obtenerGrupoOExcepcion(id);

        Curso curso = obtenerCursoOExcepcion(request.idCurso());
        Maestro maestro = obtenerMaestroOExcepcion(request.idMaestro());
        Aula aula = obtenerAulaOExcepcion(request.idAula());

        grupo.actualizarGrupo(curso, aula, maestro, request.periodo());
        log.info("Grupo actualizado...");
        return grupoMapper.entidadResponse(grupo);
    }

    @Override
    public void eliminar(Long id) {
        log.info("Eliminando informacion de grupo...");
        if (!grupoRepository.existsById(id))
            throw new IllegalArgumentException("No existe el grupo con id " + id);

        if (horarioRepository.existsByGrupoId(id))
            throw new EntidadRelacionadaException("El grupo tiene horarios asociados, no es posible borrar");

        Grupo grupo = obtenerGrupoOExcepcion(id);
        log.warn("Grupo eliminado...");
        grupoRepository.delete(grupo);

    }

    public Grupo obtenerGrupoOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(grupoRepository, id, Grupo.class);
    }
    private Curso obtenerCursoOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(cursoRepository, id, Curso.class);
    }
    private Maestro obtenerMaestroOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }
    private Aula obtenerAulaOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }
}
