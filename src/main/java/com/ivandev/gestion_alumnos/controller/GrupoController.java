package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.GrupoDTO;
import com.ivandev.gestion_alumnos.dto.GrupoResponseDTO;
import com.ivandev.gestion_alumnos.service.GrupoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;

    @GetMapping
    public ResponseEntity<List<GrupoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(grupoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<GrupoResponseDTO> crear(@Valid @RequestBody GrupoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(grupoService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> actualizar(@PathVariable Long id,
                                                       @Valid @RequestBody GrupoDTO dto) {
        return ResponseEntity.ok(grupoService.actualizar(id, dto));
    }

    @PatchMapping("/{id}/baja")
    public ResponseEntity<Void> darDeBaja(@PathVariable Long id) {
        grupoService.darDeBaja(id);
        return ResponseEntity.noContent().build();
    }
}