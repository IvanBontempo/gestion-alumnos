package com.ivandev.gestion_alumnos.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlumnoDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    private String dni;

    private String telefono;

    @Email(message = "El email no tiene formato válido")
    private String email;

    private LocalDate fechaNacimiento;
}