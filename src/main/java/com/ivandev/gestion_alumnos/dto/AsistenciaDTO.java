package com.ivandev.gestion_alumnos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsistenciaDTO {

    @NotNull(message = "El alumno es obligatorio")
    private Long alumnoId;

    @NotNull(message = "El grupo es obligatorio")
    private Long grupoId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La presencia es obligatoria")
    private Boolean presente;
}