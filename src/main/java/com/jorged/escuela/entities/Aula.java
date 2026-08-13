package com.jorged.escuela.entities;

import com.jorged.escuela.utils.StringCustomUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    @OneToMany(mappedBy = "aula", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();

    private void validarNombre(String nombre){
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "Nombre requerido y debe tener entre 1 y 50 caracteres");
    }

    public void actualizarAula(String nombre, Integer capacidad){
        validarNombre(nombre);

        this.nombre = nombre;
        this.capacidad = capacidad;
    }
}
