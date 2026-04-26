package com.ivandev.gestion_alumnos.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagoResponseDTO {
    private Long id;
    private String alumnoNombre;
    private String alumnoApellido;
    private Double monto;
    private LocalDate fechaPago;
    private Integer mes;
    private Integer anio;
    private String metodoPago;
    private String observaciones;
}