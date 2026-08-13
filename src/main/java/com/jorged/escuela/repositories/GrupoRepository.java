package com.jorged.escuela.repositories;

import com.jorged.escuela.entities.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    boolean existsByMaestroId(Long idMaestro);

    boolean existsByAulaId(Long id);

    boolean existsByCursoId(Long id);

    @Query(nativeQuery=true, value= """
        SELECT COUNT(ID_GRUPO) > 0 FROM GRUPOS WHERE ID_CURSO=:idCurso AND ID_AULA=:idAula AND ID_MAESTRO=:idMaestro
            AND PERIODO=:periodo
    """ )
    Boolean consultarDatosUnicos(@Param("idCurso") Long idCurso,
                        @Param("idAula") Long idAula,
                        @Param("idMaestro") Long idMaestro,
                                @Param("periodo") String periodo);

    @Query(nativeQuery=true, value= """
        SELECT COUNT(ID_GRUPO) > 0 FROM GRUPOS WHERE ID_CURSO=:idCurso AND ID_AULA=:idAula AND ID_MAESTRO=:idMaestro
            AND PERIODO=:periodo AND ID_GRUPO!=:id
    """ )
    Boolean consultarDatosUnicos(@Param("idCurso") Long idCurso,
                                 @Param("idAula") Long idAula,
                                 @Param("idMaestro") Long idMaestro,
                                 @Param("periodo") String periodo,
                                 @Param("id") Long id);

    boolean existsById(Long id);
}
