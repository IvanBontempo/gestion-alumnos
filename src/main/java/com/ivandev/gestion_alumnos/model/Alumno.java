package com.ivandev.gestion_alumnos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name="alumnos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Alumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nombre;

    @NotBlank
    @Column(nullable = false)
    private String apellido;

    @Column(unique = true)
    private String dni;

    private String telefono;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDate fechaNacimiento;

    private LocalDate fechaInscripcion;

    @Column(nullable = false)
    private Boolean activo = true;

}
