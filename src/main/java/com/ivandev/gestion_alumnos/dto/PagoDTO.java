package com.ivandev.gestion_alumnos.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoDTO {

    @NotNull(message = "El alumno es obligatorio")
    private Long alumnoId;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private Double monto;

    @NotNull(message = "El mes es obligatorio")
    @Min(value = 1, message = "El mes debe ser entre 1 y 12")
    @Max(value = 12, message = "El mes debe ser entre 1 y 12")
    private Integer mes;

    @NotNull(message = "El año es obligatorio")
    private Integer anio;

    private String metodoPago;

    private String observaciones;
}