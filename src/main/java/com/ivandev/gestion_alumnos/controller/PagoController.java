package com.ivandev.gestion_alumnos.controller;

import com.ivandev.gestion_alumnos.dto.PagoDTO;
import com.ivandev.gestion_alumnos.dto.PagoResponseDTO;
import com.ivandev.gestion_alumnos.service.PagoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoResponseDTO> registrar(
            @Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.registrar(dto));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<PagoResponseDTO>> porAlumno(
            @PathVariable Long alumnoId) {
        return ResponseEntity.ok(pagoService.obtenerPorAlumno(alumnoId));
    }

    @GetMapping("/mes")
    public ResponseEntity<List<PagoResponseDTO>> porMesYAnio(
            @RequestParam Integer mes,
            @RequestParam Integer anio) {
        return ResponseEntity.ok(pagoService.obtenerPorMesYAnio(mes, anio));
    }

    @GetMapping("/alumno/{alumnoId}/anio/{anio}")
    public ResponseEntity<List<PagoResponseDTO>> porAlumnoYAnio(
            @PathVariable Long alumnoId,
            @PathVariable Integer anio) {
        return ResponseEntity.ok(pagoService.obtenerPorAlumnoYAnio(alumnoId, anio));
    }
}