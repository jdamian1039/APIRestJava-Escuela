package com.jorged.escuela.entities;

import com.jorged.escuela.dto.inscripciones.InscripcionRequest;
import com.jorged.escuela.dto.inscripciones.InscripcionResponse;
import com.jorged.escuela.services.CrudService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "INSCRIPCIONES", uniqueConstraints = @UniqueConstraint(name = "INSCRIPCION_ALU_GRU_UK",
        columnNames = {"ID_ALUMNO", "ID_GRUPO" }))
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INSCRIPCION")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="ID_ALUMNO", nullable = false)
    private Alumno alumno;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="ID_GRUPO", nullable = false)
    private Grupo grupo;
    @Builder.Default
    @Column (name="FECHA_INSCRIPCION")
    private LocalDate fechaInscripcion = LocalDate.now();
    @OneToOne(mappedBy = "inscripcion")
    private Calificacion calificacion;

    public void actualizarInscripcion(Alumno alumno, Grupo grupo, LocalDate fechaInscripcion, Calificacion calificacion) {
        this.alumno = alumno;
        this.grupo = grupo;
        this.fechaInscripcion = fechaInscripcion;
        this.calificacion = calificacion;
    }
    public void llenarObjetosInscripcion(Alumno alumno, Grupo grupo, Calificacion calificacion) {
        this.alumno = alumno;
        this.grupo = grupo;
        this.calificacion = calificacion;
    }
}
