package com.ivandev.gestion_alumnos.service;

import com.ivandev.gestion_alumnos.dto.PagoDTO;
import com.ivandev.gestion_alumnos.dto.PagoResponseDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.model.Alumno;
import com.ivandev.gestion_alumnos.model.Pago;
import com.ivandev.gestion_alumnos.repository.AlumnoRepository;
import com.ivandev.gestion_alumnos.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final AlumnoRepository alumnoRepository;

    public PagoResponseDTO registrar(PagoDTO dto) {

        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alumno no encontrado con id: " + dto.getAlumnoId()));

        // Verificar si ya existe un pago para ese alumno en ese mes y año
        pagoRepository.findByAlumnoIdAndMesAndAnio(
                        dto.getAlumnoId(), dto.getMes(), dto.getAnio())
                .ifPresent(p -> {
                    throw new IllegalArgumentException(
                            "Ya existe un pago registrado para ese alumno en ese mes y año");
                });

        Pago pago = Pago.builder()
                .alumno(alumno)
                .monto(dto.getMonto())
                .fechaPago(LocalDate.now())
                .mes(dto.getMes())
                .anio(dto.getAnio())
                .metodoPago(dto.getMetodoPago())
                .observaciones(dto.getObservaciones())
                .build();

        return toResponseDTO(pagoRepository.save(pago));
    }

    public List<PagoResponseDTO> obtenerPorAlumno(Long alumnoId) {
        return pagoRepository.findByAlumnoId(alumnoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<PagoResponseDTO> obtenerPorMesYAnio(Integer mes, Integer anio) {
        return pagoRepository.findByMesAndAnio(mes, anio)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<PagoResponseDTO> obtenerPorAlumnoYAnio(Long alumnoId, Integer anio) {
        return pagoRepository.findByAlumnoIdAndAnio(alumnoId, anio)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private PagoResponseDTO toResponseDTO(Pago pago) {
        return PagoResponseDTO.builder()
                .id(pago.getId())
                .alumnoNombre(pago.getAlumno().getNombre())
                .alumnoApellido(pago.getAlumno().getApellido())
                .monto(pago.getMonto())
                .fechaPago(pago.getFechaPago())
                .mes(pago.getMes())
                .anio(pago.getAnio())
                .metodoPago(pago.getMetodoPago())
                .observaciones(pago.getObservaciones())
                .build();
    }
}