package com.ivandev.gestion_alumnos.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String nombre;
    private String rol;
}