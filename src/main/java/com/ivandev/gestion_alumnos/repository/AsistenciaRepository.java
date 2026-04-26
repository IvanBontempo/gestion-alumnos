package com.ivandev.gestion_alumnos.repository;

import com.ivandev.gestion_alumnos.model.Asistencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    List<Asistencia> findByGrupoIdAndFecha(Long grupoId, LocalDate fecha);

    List<Asistencia> findByAlumnoId(Long alumnoId);

    List<Asistencia> findByGrupoId(Long grupoId);

    Optional<Asistencia> findByAlumnoIdAndGrupoIdAndFecha(
            Long alumnoId, Long grupoId, LocalDate fecha);
}