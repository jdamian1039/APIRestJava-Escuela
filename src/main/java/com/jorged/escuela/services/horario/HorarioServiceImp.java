package com.jorged.escuela.services.horario;

import com.jorged.escuela.dto.horarios.HorarioRequest;
import com.jorged.escuela.dto.horarios.HorarioResponse;
import com.jorged.escuela.entities.Grupo;
import com.jorged.escuela.entities.Horario;
import com.jorged.escuela.enums.DiaSemana;
import com.jorged.escuela.mapper.HorarioMapper;
import com.jorged.escuela.repositories.HorarioRepository;
import com.jorged.escuela.services.grupos.GrupoServiceImp;
import com.jorged.escuela.utils.ServiceUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class HorarioServiceImp implements HorarioService {

    private final HorarioRepository horarioRepository;
    private final HorarioMapper horarioMapper;
    private final GrupoServiceImp grupoServiceImp;

    @Override
    @Transactional(readOnly = true)
    public List<HorarioResponse> listar() {
        log.info("Listando horarios...");
        return horarioRepository.findAll().stream().map(horarioMapper::entidadResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HorarioResponse obtenerPorId(Long id) {
        log.info("Mostrando horario {}...", id);
        return horarioMapper.entidadResponse(obtenerHorarioOExcepcion(id));
    }

    @Override
    public HorarioResponse registrar(HorarioRequest request) {
        log.info("Generando nuevo horario...");
        log.info("Validando datos de la peticion...");
        DiaSemana dia = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());
        Long horaInicio = Long.parseLong(Arrays.stream(request.horaInicio().split(":")).findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Horario de inicio no cumple con el formato HH:mm")));
        Long horaFin = Long.parseLong(Arrays.stream(request.horaFin().split(":")).findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Horario de término no cumple con el formato HH:mm")));

        if (horarioRepository.consultarDatosUnicos(request.idGrupo(), dia.toString(), horaInicio, horaFin))
            throw new IllegalArgumentException("Los horarios se empalman, valide sus datos de entrada");

        log.info("Iniciando insercion...");
        Grupo grupo = grupoServiceImp.obtenerGrupoOExcepcion(request.idGrupo());
        Horario horario = horarioMapper.requestAEntidad(request, dia, grupo);
        horarioRepository.save(horario);
        log.info("Horario guardado...");
        return horarioMapper.entidadResponse(horario);
    }

    @Override
    public HorarioResponse actualizar(HorarioRequest request, Long id) {
        log.info("Actualizando horario {}...", id);
        log.info("Validando datos de la peticion...");
        DiaSemana dia = DiaSemana.obtenerDiaSemanaPorDescripcion(request.dia());

        Long horaInicio = Long.parseLong(Arrays.stream(request.horaInicio().split(":")).findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Horario de Inicio no cumple con el formato HH:mm")));
        Long horaFin = Long.parseLong(Arrays.stream(request.horaFin().split(":")).findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Horario de Fin no cumple con el formato HH:mm")));
        if (horaInicio > horaFin)
            throw new IllegalArgumentException("Hora de inicio no puede ser posterior a la hora de término");

        if (horarioRepository.consultarDatosUnicos(request.idGrupo(), dia.toString(), horaInicio, horaFin, id))
            throw new IllegalArgumentException("Los horarios se empalman, valide sus datos de entrada");

        log.info("Insertando datos");
        Grupo grupo = grupoServiceImp.obtenerGrupoOExcepcion(request.idGrupo());
        Horario horario = obtenerHorarioOExcepcion(id);
        horario.actualizarHorario(grupo, dia, request.horaInicio(), request.horaFin());
        log.info("Horario actualizado...");
        return horarioMapper.entidadResponse(horario);
    }

    @Override
    public void eliminar(Long id) {
        log.warn("Borrando horario...");
        Horario horario = obtenerHorarioOExcepcion(id);

        horarioRepository.delete(horario);
    }

    private Horario obtenerHorarioOExcepcion(Long id){
        return ServiceUtils.obtenerEntidadOException(horarioRepository, id, Horario.class);
    }
}
