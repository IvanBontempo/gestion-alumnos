package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.AsistenciaDTO;
import com.ivandev.gestion_alumnos.dto.AsistenciaResponseDTO;
import com.ivandev.gestion_alumnos.service.AsistenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/asistencias")
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService;

    @PostMapping
    public ResponseEntity<AsistenciaResponseDTO> registrar(
            @Valid @RequestBody AsistenciaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(asistenciaService.registrar(dto));
    }

    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<AsistenciaResponseDTO>> porGrupoYFecha(
            @PathVariable Long grupoId,
            @RequestParam(required = false) LocalDate fecha) {
        if (fecha != null) {
            return ResponseEntity.ok(asistenciaService.obtenerPorGrupoYFecha(grupoId, fecha));
        }
        return ResponseEntity.ok(asistenciaService.obtenerPorGrupo(grupoId));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<AsistenciaResponseDTO>> porAlumno(
            @PathVariable Long alumnoId) {
        return ResponseEntity.ok(asistenciaService.obtenerPorAlumno(alumnoId));
    }
}