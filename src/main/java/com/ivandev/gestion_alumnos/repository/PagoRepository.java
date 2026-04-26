package com.ivandev.gestion_alumnos.repository;

import com.ivandev.gestion_alumnos.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    List<Pago> findByAlumnoId(Long alumnoId);

    List<Pago> findByMesAndAnio(Integer mes, Integer anio);

    Optional<Pago> findByAlumnoIdAndMesAndAnio(Long alumnoId, Integer mes, Integer anio);

    List<Pago> findByAlumnoIdAndAnio(Long alumnoId, Integer anio);
}