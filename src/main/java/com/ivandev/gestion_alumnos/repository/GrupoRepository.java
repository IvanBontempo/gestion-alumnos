package com.ivandev.gestion_alumnos.repository;


import com.ivandev.gestion_alumnos.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    List<Grupo> findByActivoTrue();

    List<Grupo> findByProfesorId(Long profesorId);
}
