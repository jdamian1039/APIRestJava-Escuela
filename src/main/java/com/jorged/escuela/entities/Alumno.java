package com.jorged.escuela.entities;

import com.jorged.escuela.dto.datos.DatosCalificacion;
import com.jorged.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="ALUMNOS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ALUMNO")
    private Long id;
    @Column(name = "NOMBRE", length = 50, nullable = false)
    private String nombre;
    @Column(name = "APELLIDO_PATERNO", length = 50, nullable = false)
    private String apellidoPaterno;
    @Column(name = "APELLIDO_MATERNO", length = 50, nullable = false)
    private String apellidoMaterno;
    @Column(name = "EMAIL", length = 100, unique=true, nullable = false)
    private String email;
    @Column(name = "MATRICULA", length = 10, unique=true, nullable = false)
    private String matricula;
    @Builder.Default
    @Column(name = "FECHA_INGRESO")
    private LocalDate fechaIngreso = LocalDate.now();
    @Builder.Default
    @OneToMany(mappedBy = "alumno")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    private void validarDatos(String nombre, String apellidoPaterno, String apellidoMaterno){
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "Nombre requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoPaterno, 1, 50,
                "Apellido paterno requerido y debe tener entre 1 y 50 caracteres");

        StringCustomUtils.validarTamanio(apellidoMaterno, 1, 50,
                "Apellido Materno y debe tener entre 1 y 50 caracteres");


    }

    public boolean cambioEnDatos(String nombre, String apellidoPaterno, String apellidoMaterno) {
        return !this.nombre.equals(nombre) || !this.apellidoPaterno.equals(apellidoPaterno) ||
        !this.apellidoMaterno.equals(apellidoMaterno);
    }

    public void asignarDatosAcademicos(String email, String matricula){
        StringCustomUtils.validarTamanio(email, 1, 100,
                "Email requerido y debe tener entre 1 y 100 caracteres");

        StringCustomUtils.validarTamanio(matricula, 10, 10,
                "Matricula requerido y debe tener 10 caracteres");
        this.email = email.toLowerCase().trim();
        this.matricula = matricula.trim();
    }

    public void actualizarAlumno(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String matricula) {
        validarDatos(nombre, apellidoPaterno, apellidoMaterno);

        asignarDatosAcademicos(email, matricula);

        this.nombre = nombre.trim();
        this.apellidoPaterno = apellidoPaterno.trim();
        this.apellidoMaterno = apellidoMaterno.trim();

    }

    public BigDecimal calcularPromedio(){
        List<BigDecimal> calificaciones = inscripciones.stream().map(Inscripcion::getCalificacion)
                .filter(Objects::nonNull).map(Calificacion::getCalificacion).filter(Objects::nonNull).toList();
        if (calificaciones.isEmpty())
            return BigDecimal.ZERO;

        BigDecimal suma = calificaciones.stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return suma.divide(
                BigDecimal.valueOf(calificaciones.size()),
                2, RoundingMode.HALF_UP
        );
    }
}
