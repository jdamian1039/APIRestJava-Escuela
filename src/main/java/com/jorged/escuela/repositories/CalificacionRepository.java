package com.jorged.escuela.repositories;

import com.jorged.escuela.entities.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {

    boolean existsByInscripcionId(Long inscripcionId);

    boolean existsByInscripcionIdAndIdNot(Long inscripcionId, Long id);
    
    Calificacion getCalificacionByInscripcionId(Long inscripcionId);
}
