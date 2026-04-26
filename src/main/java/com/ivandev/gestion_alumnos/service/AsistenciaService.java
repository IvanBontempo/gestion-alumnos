package com.ivandev.gestion_alumnos.service;

import com.ivandev.gestion_alumnos.dto.AsistenciaDTO;
import com.ivandev.gestion_alumnos.dto.AsistenciaResponseDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.model.*;
import com.ivandev.gestion_alumnos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;

    public AsistenciaResponseDTO registrar(AsistenciaDTO dto) {

        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alumno no encontrado con id: " + dto.getAlumnoId()));

        Grupo grupo = grupoRepository.findById(dto.getGrupoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo no encontrado con id: " + dto.getGrupoId()));

        // Si ya existe una asistencia para ese alumno, grupo y fecha, la actualizamos
        Asistencia asistencia = asistenciaRepository
                .findByAlumnoIdAndGrupoIdAndFecha(
                        dto.getAlumnoId(), dto.getGrupoId(), dto.getFecha())
                .orElse(Asistencia.builder()
                        .alumno(alumno)
                        .grupo(grupo)
                        .fecha(dto.getFecha())
                        .build());

        asistencia.setPresente(dto.getPresente());

        return toResponseDTO(asistenciaRepository.save(asistencia));
    }

    public List<AsistenciaResponseDTO> obtenerPorGrupoYFecha(Long grupoId, LocalDate fecha) {
        return asistenciaRepository.findByGrupoIdAndFecha(grupoId, fecha)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AsistenciaResponseDTO> obtenerPorAlumno(Long alumnoId) {
        return asistenciaRepository.findByAlumnoId(alumnoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<AsistenciaResponseDTO> obtenerPorGrupo(Long grupoId) {
        return asistenciaRepository.findByGrupoId(grupoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private AsistenciaResponseDTO toResponseDTO(Asistencia asistencia) {
        return AsistenciaResponseDTO.builder()
                .id(asistencia.getId())
                .alumnoNombre(asistencia.getAlumno().getNombre())
                .alumnoApellido(asistencia.getAlumno().getApellido())
                .grupoNombre(asistencia.getGrupo().getNombre())
                .fecha(asistencia.getFecha())
                .presente(asistencia.getPresente())
                .build();
    }
}