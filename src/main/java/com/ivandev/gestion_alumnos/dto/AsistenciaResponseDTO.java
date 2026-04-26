package com.ivandev.gestion_alumnos.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaResponseDTO {
    private Long id;
    private String alumnoNombre;
    private String alumnoApellido;
    private String grupoNombre;
    private LocalDate fecha;
    private Boolean presente;
}