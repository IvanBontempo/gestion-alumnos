package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.AlumnoDTO;
import com.ivandev.gestion_alumnos.model.Alumno;
import com.ivandev.gestion_alumnos.service.AlumnoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<Alumno>> obtenerTodos() {
        return ResponseEntity.ok(alumnoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<Alumno> crear(@Valid @RequestBody AlumnoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(@PathVariable Long id,
                                             @Valid @RequestBody AlumnoDTO dto) {
        return ResponseEntity.ok(alumnoService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        alumnoService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/alta")
    public ResponseEntity<Void> darDeAlta(@PathVariable Long id) {
        alumnoService.darDeAlta(id);
        return ResponseEntity.noContent().build();
    }
}