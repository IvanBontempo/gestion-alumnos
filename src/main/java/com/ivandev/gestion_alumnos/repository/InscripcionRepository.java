package com.ivandev.gestion_alumnos.repository;

import com.ivandev.gestion_alumnos.model.EstadoInscripcion;
import com.ivandev.gestion_alumnos.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByGrupoIdAndEstado(Long grupoId, EstadoInscripcion estado);

    List<Inscripcion> findByAlumnoId(Long alumnoId);

    Optional<Inscripcion> findByAlumnoIdAndGrupoIdAndEstadoNot(
            Long alumnoId, Long grupoId, EstadoInscripcion estado);

    long countByGrupoIdAndEstado(Long grupoId, EstadoInscripcion estado);

    List<Inscripcion> findByGrupoIdAndEstadoOrderByFechaInscripcionAsc(
            Long grupoId, EstadoInscripcion estado);

    List<Inscripcion> findByEstado(EstadoInscripcion estado);
}