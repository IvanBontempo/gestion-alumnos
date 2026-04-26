package com.ivandev.gestion_alumnos.dto;

import com.ivandev.gestion_alumnos.model.EstadoInscripcion;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InscripcionResponseDTO {
    private Long id;
    private String alumnoNombre;
    private String alumnoApellido;
    private String grupoNombre;
    private EstadoInscripcion estado;
    private LocalDate fechaInscripcion;
    private Long alumnoId;
}