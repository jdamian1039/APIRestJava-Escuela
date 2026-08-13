package com.jorged.escuela.repositories;

import com.jorged.escuela.dto.datos.DatosCurso;
import com.jorged.escuela.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    @Query(nativeQuery=true, value= """
        SELECT GENERAR_MATRICULA(:nombre, :apellidoPaterno, :apellidoMaterno) FROM DUAL
    """ )
    String generarMatricula(@Param("nombre") String nombre,
                            @Param("apellidoPaterno") String apellidoPaterno,
                            @Param("apellidoMaterno") String apellidoMaterno);

    @Query(nativeQuery=true, value= """
        SELECT GENERAR_EMAIL(:nombre, :apellidoPaterno, :apellidoMaterno) FROM DUAL
    """ )
    String generarEmail(@Param("nombre") String nombre,
                            @Param("apellidoPaterno") String apellidoPaterno,
                            @Param("apellidoMaterno") String apellidoMaterno);

    //boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
    //boolean existsByMatriculaIgnoreCaseAndIdNot(String matricula, Long id);
}
