package com.app.gymtracker.repository;

import com.app.gymtracker.model.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    List<Ejercicio> findByGrupoMuscular(String grupoMuscular);
    List<Ejercicio> findByNombreContainingIgnoreCase(String nombre);
}