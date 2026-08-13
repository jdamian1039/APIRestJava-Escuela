package com.jorged.escuela.entities;

import com.jorged.escuela.enums.DiaSemana;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HORARIOS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HORARIO")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name="ID_GRUPO", nullable = false)
    private Grupo grupo;
    @Enumerated(EnumType.STRING)
    @Column(name = "DIA", nullable = false)
    private DiaSemana dia;
    @Column(name = "HORA_INICIO", length = 5, nullable = false)
    private String horaInicio;
    @Column(name = "HORA_FIN", length = 5, nullable = false)
    private String horaFin;

    public void asignarElementosHorario(Grupo grupo, DiaSemana dia) {
        this.grupo = grupo;
        this.dia = dia;
    }

    public void actualizarHorario(Grupo grupo, DiaSemana dia, String horaInicio, String horaFin) {
        this.grupo = grupo;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
