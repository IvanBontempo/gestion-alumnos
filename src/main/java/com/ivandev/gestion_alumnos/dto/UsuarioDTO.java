package com.ivandev.gestion_alumnos.dto;


import com.ivandev.gestion_alumnos.model.Rol;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Email(message = "El email no tiene formato válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @NotNull(message = "El rol es obligatorio")
    private Rol rol;
}