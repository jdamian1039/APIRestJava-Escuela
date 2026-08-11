package com.jorged.escuela.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_AULA")
    private Long id;
    @Column(name = "NOMBRE", unique=true, length = 100, nullable = false)
    private String nombre;
    @Column(name = "CAPACIDAD", nullable = false)
    private Integer capacidad;
}
