package com.ivandev.gestion_alumnos.dto;


import com.ivandev.gestion_alumnos.model.Rol;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private Rol rol;
    private Boolean activo;
}