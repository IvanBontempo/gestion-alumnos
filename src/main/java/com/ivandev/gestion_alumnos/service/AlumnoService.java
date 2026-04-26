package com.ivandev.gestion_alumnos.service;



import com.ivandev.gestion_alumnos.dto.AlumnoDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.model.Alumno;
import com.ivandev.gestion_alumnos.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final AlumnoRepository alumnoRepository;

    // Obtener todos los alumnos
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    // Obtener un alumno por id
    public Alumno obtenerPorId(Long id) {
        return alumnoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con id: " + id));
    }

    // Crear un alumno nuevo
    public Alumno crear(AlumnoDTO dto) {
        if (alumnoRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe un alumno con ese DNI");
        }
        if (alumnoRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un alumno con ese email");
        }

        Alumno alumno = Alumno.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .dni(dto.getDni())
                .telefono(dto.getTelefono())
                .email(dto.getEmail())
                .fechaNacimiento(dto.getFechaNacimiento())
                .fechaInscripcion(LocalDate.now())
                .activo(true)
                .build();

        return alumnoRepository.save(alumno);
    }

    // Actualizar un alumno
    public Alumno actualizar(Long id, AlumnoDTO dto) {
        Alumno alumno = obtenerPorId(id);

        alumno.setNombre(dto.getNombre());
        alumno.setApellido(dto.getApellido());
        alumno.setTelefono(dto.getTelefono());
        alumno.setEmail(dto.getEmail());
        alumno.setFechaNacimiento(dto.getFechaNacimiento());

        return alumnoRepository.save(alumno);
    }

    // Dar de baja (no eliminamos, solo desactivamos)
    public void darDeBaja(Long id) {
        Alumno alumno = obtenerPorId(id);
        alumno.setActivo(false);
        alumnoRepository.save(alumno);
    }

    // Eliminar definitivamente
    public void eliminar(Long id) {
        Alumno alumno = obtenerPorId(id);
        alumnoRepository.delete(alumno);
    }

    public void darDeAlta(Long id) {
        Alumno alumno = obtenerPorId(id);
        alumno.setActivo(true);
        alumnoRepository.save(alumno);
    }
}