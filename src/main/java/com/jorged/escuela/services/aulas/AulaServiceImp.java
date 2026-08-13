package com.jorged.escuela.services.aulas;

import com.jorged.escuela.dto.aulas.AulaRequest;
import com.jorged.escuela.dto.aulas.AulaResponse;
import com.jorged.escuela.entities.Alumno;
import com.jorged.escuela.entities.Aula;
import com.jorged.escuela.entities.Maestro;
import com.jorged.escuela.exceptions.EntidadRelacionadaException;
import com.jorged.escuela.mapper.AulaMapper;
import com.jorged.escuela.repositories.AulaRepository;
import com.jorged.escuela.repositories.GrupoRepository;
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
public class AulaServiceImp implements AulaService{

    private final AulaRepository aulaRepository;
    private final AulaMapper aulaMapper;
    private final GrupoRepository grupoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AulaResponse> listar() {
        return aulaRepository.findAll().stream().map(aulaMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponse obtenerPorId(Long id) {
        return aulaMapper.entidadResponse(obtenerAula(id));
    }

    @Override
    public AulaResponse registrar(AulaRequest request) {
        log.info("Registrando nueva aula...");
        Aula aula = aulaMapper.requestAEntidad(request);
        if (validarNombreAula(aula.getNombre()))
            throw new IllegalArgumentException("Ya existe una aula con el nombre de " + aula.getNombre());

        aulaRepository.save(aula);
        log.info("Aula {} registrada correctamente", aula.getNombre());
        return aulaMapper.entidadResponse(aula);
    }

    @Override
    public AulaResponse actualizar(AulaRequest request, Long id) {
        log.info("Modificando datos de aula...");
        Aula aula = obtenerAula(id);
        if (validarNombreAula(request.nombre(), id))
            throw new IllegalArgumentException("Ya existe una aula con el nombre de " + aula.getNombre());

        aula.actualizarAula(request.nombre(), request.capacidad());
        log.info("Aula {} actualizada correctamente", request.nombre());

        return aulaMapper.entidadResponse(aula);
    }

    @Override
    public void eliminar(Long id) {
        Aula aula = obtenerAula(id);
        if (grupoRepository.existsByAulaId(id))
            throw new EntidadRelacionadaException("No se puede eliminar el aula. Tiene grupos asignados");
        aulaRepository.delete(aula);
    }

    private Aula obtenerAula(Long id){
        return  ServiceUtils.obtenerEntidadOException(aulaRepository, id, Aula.class);
    }

    private boolean validarNombreAula(String nombre){
        return aulaRepository.existsByNombreIgnoreCase(nombre);
    }
    private boolean validarNombreAula(String nombre, Long id){
        return aulaRepository.existsByNombreIgnoreCaseAndIdNot(nombre, id);
    }
}
