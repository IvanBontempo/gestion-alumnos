package com.ivandev.gestion_alumnos.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grupos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grupo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private Integer capacidadMaxima;

    private String horario;

    private String diasSemana;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Usuario profesor;

    @Column(nullable = false)
    private Boolean activo = true;
}