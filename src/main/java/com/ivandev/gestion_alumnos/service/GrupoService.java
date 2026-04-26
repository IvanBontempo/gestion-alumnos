package com.ivandev.gestion_alumnos.service;

import com.ivandev.gestion_alumnos.dto.GrupoDTO;
import com.ivandev.gestion_alumnos.dto.GrupoResponseDTO;
import com.ivandev.gestion_alumnos.exception.ResourceNotFoundException;
import com.ivandev.gestion_alumnos.model.Grupo;
import com.ivandev.gestion_alumnos.model.Usuario;
import com.ivandev.gestion_alumnos.repository.GrupoRepository;
import com.ivandev.gestion_alumnos.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {

    private final GrupoRepository grupoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<GrupoResponseDTO> obtenerTodos() {
        return grupoRepository.findByActivoTrue()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public GrupoResponseDTO obtenerPorId(Long id) {
        return toResponseDTO(grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado con id: " + id)));
    }

    public GrupoResponseDTO crear(GrupoDTO dto) {
        Usuario profesor = null;
        if (dto.getProfesorId() != null) {
            profesor = usuarioRepository.findById(dto.getProfesorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + dto.getProfesorId()));
        }

        Grupo grupo = Grupo.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .capacidadMaxima(dto.getCapacidadMaxima())
                .horario(dto.getHorario())
                .diasSemana(dto.getDiasSemana())
                .profesor(profesor)
                .activo(true)
                .build();

        return toResponseDTO(grupoRepository.save(grupo));
    }

    public GrupoResponseDTO actualizar(Long id, GrupoDTO dto) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado con id: " + id));

        Usuario profesor = null;
        if (dto.getProfesorId() != null) {
            profesor = usuarioRepository.findById(dto.getProfesorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Profesor no encontrado con id: " + dto.getProfesorId()));
        }

        grupo.setNombre(dto.getNombre());
        grupo.setDescripcion(dto.getDescripcion());
        grupo.setCapacidadMaxima(dto.getCapacidadMaxima());
        grupo.setHorario(dto.getHorario());
        grupo.setDiasSemana(dto.getDiasSemana());
        grupo.setProfesor(profesor);

        return toResponseDTO(grupoRepository.save(grupo));
    }

    public void darDeBaja(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grupo no encontrado con id: " + id));
        grupo.setActivo(false);
        grupoRepository.save(grupo);
    }

    private GrupoResponseDTO toResponseDTO(Grupo grupo) {
        return GrupoResponseDTO.builder()
                .id(grupo.getId())
                .nombre(grupo.getNombre())
                .descripcion(grupo.getDescripcion())
                .capacidadMaxima(grupo.getCapacidadMaxima())
                .horario(grupo.getHorario())
                .diasSemana(grupo.getDiasSemana())
                .profesorNombre(grupo.getProfesor() != null
                        ? grupo.getProfesor().getNombre()
                        : null)
                .activo(grupo.getActivo())
                .build();
    }
}