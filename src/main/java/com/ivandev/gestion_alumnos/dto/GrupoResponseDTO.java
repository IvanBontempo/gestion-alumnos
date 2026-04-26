package com.ivandev.gestion_alumnos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrupoResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private Integer capacidadMaxima;
    private String horario;
    private String diasSemana;
    private String profesorNombre;
    private Boolean activo;
}