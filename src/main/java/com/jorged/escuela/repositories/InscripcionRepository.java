package com.jorged.escuela.repositories;

import com.jorged.escuela.entities.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    boolean existsByAlumnoId(Long idAlumno);

    boolean existsByAlumnoIdAndGrupoId(Long alumnoId, Long grupoId);
    boolean existsByAlumnoIdAndGrupoIdAndIdNot(Long alumnoId, Long grupoId, Long id);
}
