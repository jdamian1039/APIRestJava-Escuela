package com.jorged.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "CALIFICACIONES")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Calificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CALIFICACION", length = 10)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="ID_INSCRIPCION", unique=true, nullable = false)
    private Inscripcion inscripcion;

    @Column (name="CALIFICACION", nullable = false)
    private BigDecimal calificacion;

    @Builder.Default
    @Column (name="FECHA_REGISTRO", nullable = false)
    private LocalDate fechaRegistro = LocalDate.now();

    public void actualizarCalificacion(Inscripcion inscripcion, BigDecimal calificacion, LocalDate fechaRegistro) {
        this.inscripcion = inscripcion;
        this.calificacion = calificacion;
        this.fechaRegistro = fechaRegistro;
    }
    public void asignarObjeto(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }
}
