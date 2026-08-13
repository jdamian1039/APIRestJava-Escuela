package com.jorged.escuela.repositories;

import com.jorged.escuela.entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long>  {
    boolean existsByGrupoId(Long grupoId);

    @Query(nativeQuery=true, value= """
        SELECT COUNT(ID_HORARIO) > 0 FROM HORARIOS WHERE ID_GRUPO=:idGrupo AND DIA=:dia 
            AND TO_NUMBER(SUBSTR(HORA_INICIO, 1, INSTR(HORA_INICIO, ':') - 1)) BETWEEN :horaInicio AND :horaFin
            AND TO_NUMBER(SUBSTR(HORA_FIN, 1, INSTR(HORA_FIN, ':') - 1)) BETWEEN :horaInicio AND :horaFin
    """ )
    Boolean consultarDatosUnicos(@Param("idGrupo") Long idGrupo,
                                 @Param("dia") String dia,
                                 @Param("horaInicio") Long horaInicio,
                                 @Param("horaFin") Long horaFin);

    @Query(nativeQuery=true, value= """
        SELECT COUNT(ID_HORARIO) > 0 FROM HORARIOS WHERE ID_GRUPO=:idGrupo AND DIA=:dia 
            AND TO_NUMBER(SUBSTR(HORA_INICIO, 1, INSTR(HORA_INICIO, ':') - 1)) BETWEEN :horaInicio AND :horaFin
            AND TO_NUMBER(SUBSTR(HORA_FIN, 1, INSTR(HORA_FIN, ':') - 1)) BETWEEN :horaInicio AND :horaFin
            AND ID_HORARIO != :idHorario
    """ )
    Boolean consultarDatosUnicos(@Param("idGrupo") Long idGrupo,
                                 @Param("dia") String dia,
                                 @Param("horaInicio") Long horaInicio,
                                 @Param("horaFin") Long horaFin,
                                 @Param("idHorario") Long id);
}
