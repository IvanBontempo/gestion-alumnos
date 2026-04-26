package com.ivandev.gestion_alumnos.service;

import com.ivandev.gestion_alumnos.dto.InscripcionDTO;
import com.ivandev.gestion_alumnos.dto.InscripcionResponseDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.model.*;
import com.ivandev.gestion_alumnos.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final AlumnoRepository alumnoRepository;
    private final GrupoRepository grupoRepository;
    private final EmailService emailService;

    public InscripcionResponseDTO inscribir(InscripcionDTO dto) {

        // 1. Verificar que existen el alumno y el grupo
        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Alumno no encontrado con id: " + dto.getAlumnoId()));

        Grupo grupo = grupoRepository.findById(dto.getGrupoId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Grupo no encontrado con id: " + dto.getGrupoId()));

        // 2. Verificar que el alumno no esté ya inscripto en el grupo
        inscripcionRepository.findByAlumnoIdAndGrupoIdAndEstadoNot(
                        dto.getAlumnoId(), dto.getGrupoId(), EstadoInscripcion.BAJA)
                .ifPresent(i -> {
                    throw new IllegalArgumentException(
                            "El alumno ya está inscripto en este grupo");
                });

        // 3. Contar cuántos alumnos activos hay en el grupo
        long activos = inscripcionRepository.countByGrupoIdAndEstado(
                dto.getGrupoId(), EstadoInscripcion.ACTIVO);

        // 4. Determinar el estado según la capacidad
        EstadoInscripcion estado = activos < grupo.getCapacidadMaxima()
                ? EstadoInscripcion.ACTIVO
                : EstadoInscripcion.LISTA_ESPERA;

        // 5. Crear y guardar la inscripción
        Inscripcion inscripcion = Inscripcion.builder()
                .alumno(alumno)
                .grupo(grupo)
                .estado(estado)
                .fechaInscripcion(LocalDate.now())
                .build();

        // Enviar email de bienvenida si quedó activo
        if (estado == EstadoInscripcion.ACTIVO && alumno.getEmail() != null) {
            emailService.enviarBienvenida(alumno.getEmail(), alumno.getNombre());
        }

        return toResponseDTO(inscripcionRepository.save(inscripcion));
    }

    public void darDeBaja(Long id) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Inscripción no encontrada con id: " + id));

        EstadoInscripcion estadoAnterior = inscripcion.getEstado();

        inscripcion.setEstado(EstadoInscripcion.BAJA);
        inscripcionRepository.save(inscripcion);

        if (estadoAnterior == EstadoInscripcion.ACTIVO) {
            promoverDeLista(inscripcion.getGrupo().getId());
        }
    }

    public List<InscripcionResponseDTO> obtenerActivosPorGrupo(Long grupoId) {
        return inscripcionRepository
                .findByGrupoIdAndEstado(grupoId, EstadoInscripcion.ACTIVO)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<InscripcionResponseDTO> obtenerListaEsperaPorGrupo(Long grupoId) {
        return inscripcionRepository
                .findByGrupoIdAndEstadoOrderByFechaInscripcionAsc(
                        grupoId, EstadoInscripcion.LISTA_ESPERA)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<InscripcionResponseDTO> obtenerPorAlumno(Long alumnoId) {
        return inscripcionRepository.findByAlumnoId(alumnoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private void promoverDeLista(Long grupoId) {
        inscripcionRepository
                .findByGrupoIdAndEstadoOrderByFechaInscripcionAsc(
                        grupoId, EstadoInscripcion.LISTA_ESPERA)
                .stream()
                .findFirst()
                .ifPresent(i -> {
                    i.setEstado(EstadoInscripcion.ACTIVO);
                    inscripcionRepository.save(i);

                    // Notificar al alumno promovido
                    if (i.getAlumno().getEmail() != null) {
                        emailService.enviarPromovido(
                                i.getAlumno().getEmail(),
                                i.getAlumno().getNombre(),
                                i.getGrupo().getNombre());
                    }
                });
    }

    private InscripcionResponseDTO toResponseDTO(Inscripcion inscripcion) {
        return InscripcionResponseDTO.builder()
                .id(inscripcion.getId())
                .alumnoNombre(inscripcion.getAlumno().getNombre())
                .alumnoApellido(inscripcion.getAlumno().getApellido())
                .grupoNombre(inscripcion.getGrupo().getNombre())
                .estado(inscripcion.getEstado())
                .fechaInscripcion(inscripcion.getFechaInscripcion())
                .alumnoId(inscripcion.getAlumno().getId())
                .build();
    }
}