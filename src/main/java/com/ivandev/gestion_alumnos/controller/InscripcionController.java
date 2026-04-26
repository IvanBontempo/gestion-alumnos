package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.InscripcionDTO;
import com.ivandev.gestion_alumnos.dto.InscripcionResponseDTO;
import com.ivandev.gestion_alumnos.service.InscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {

    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> inscribir(
            @Valid @RequestBody InscripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(inscripcionService.inscribir(dto));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        inscripcionService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/grupo/{grupoId}/activos")
    public ResponseEntity<List<InscripcionResponseDTO>> activosPorGrupo(
            @PathVariable Long grupoId) {
        return ResponseEntity.ok(inscripcionService.obtenerActivosPorGrupo(grupoId));
    }

    @GetMapping("/grupo/{grupoId}/lista-espera")
    public ResponseEntity<List<InscripcionResponseDTO>> listaEsperaPorGrupo(
            @PathVariable Long grupoId) {
        return ResponseEntity.ok(inscripcionService.obtenerListaEsperaPorGrupo(grupoId));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<InscripcionResponseDTO>> porAlumno(
            @PathVariable Long alumnoId) {
        return ResponseEntity.ok(inscripcionService.obtenerPorAlumno(alumnoId));
    }
}