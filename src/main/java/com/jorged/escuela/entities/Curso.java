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
@Table(name = "CURSOS")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CURSO", length = 10)
    private Long id;
    @Column(name = "NOMBRE", length = 100, unique=true, nullable = false)
    private String nombre;
    @Column(name = "DESCRIPCION", length = 200)
    private String descripcion;
    @Column(name = "CREDITOS", nullable = false)
    private Integer creditos;

    @Builder.Default
    @OneToMany(mappedBy = "curso", fetch = FetchType.LAZY)
    private List<Grupo> grupos = new ArrayList<>();

    public void actualizar(String nombre, String descripcion, Integer creditos) {
        validarNombre(nombre);

        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creditos = creditos;
    }

    private void validarNombre(String nombre){
        StringCustomUtils.validarTamanio(nombre, 1, 50,
                "Nombre requerido y debe tener entre 1 y 50 caracteres");
    }

}
