package com.jorged.escuela.services.maestros;

import com.jorged.escuela.dto.maestros.MaestroRequest;
import com.jorged.escuela.dto.maestros.MaestroResponse;
import com.jorged.escuela.entities.Maestro;
import com.jorged.escuela.mapper.MaestroMapper;
import com.jorged.escuela.repositories.MaestroRepository;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class MaestroServiceImp implements MaestroService {

    private final MaestroRepository maestroRepository;
    private final MaestroMapper maestroMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MaestroResponse> listar() {
        log.info("Listando todos los maestros");
        return maestroRepository.findAll().stream().map(maestroMapper::entidadResponse).toList();
    }

    @Override
    public MaestroResponse obtenerPorId(Long id) {
        log.info("Buscando maestro por id " + id);
        return maestroMapper.entidadResponse(obtenerMaestro(id));
    }

    @Override
    public MaestroResponse registrar(MaestroRequest request) {
        log.info("Registrando nuevo maestro...");
        validarDatosUnicos(request);
        Maestro maestro = maestroMapper.requestAEntidad(request);
        maestroRepository.save(maestro);

        log.info("Nuevo maestro {} {} {} registrado ", maestro.getNombre(), maestro.getApellidoPaterno(),
                maestro.getApellidoMaterno());
        return maestroMapper.entidadResponse(maestro);
    }

    @Override
    public MaestroResponse actualizar(MaestroRequest request, Long id) {
        Maestro maestro = obtenerMaestro(id);

        validarCambiosUnicos(request.email(), request.telefono(), id);

        maestro.actualizar(request.nombre(), request.apellidoPaterno(),
                request.apellidoMaterno(), request.email(), request.telefono());

        log.info("Maestro {} {} {} actualizado", maestro.getNombre(), maestro.getApellidoPaterno(),
                maestro.getApellidoMaterno());

        return maestroMapper.entidadResponse(maestro);
    }

    @Override
    public void eliminar(Long id) {
        Maestro maestro = obtenerMaestro(id);

        maestroRepository.delete(maestro);
        log.info("Maestro {} eliminado correctamente", maestro.getNombre());
    }

    private Maestro obtenerMaestro(Long id){
        return  ServiceUtils.obtenerEntidadOException(maestroRepository, id, Maestro.class);
    }

    private void validarDatosUnicos(MaestroRequest request){
        log.info("Validando email unico...");
        if (maestroRepository.existsByEmailIgnoreCase(request.email().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el email " + request.email());

        log.info("Validando telefono unico...");
        if (maestroRepository.existsByTelefono(request.telefono().trim()))
            throw new IllegalArgumentException("Ya existe un maestro registrado con el telefono " +
                    request.telefono());

    }

    private void validarCambiosUnicos(String email, String telefono, Long id){
        log.info("Validando email unico...");
        if (maestroRepository.existsByEmailIgnoreCaseAndIdNot(email, id))
            throw new IllegalArgumentException("Ya existe otro maestro registrado con el email " + email);

        log.info("Validando telefono unico...");
        if (maestroRepository.existsByTelefonoAndIdNot(telefono, id))
            throw new IllegalArgumentException("Ya existe otro maestro registrado con el telefono " + telefono);


    }
}
