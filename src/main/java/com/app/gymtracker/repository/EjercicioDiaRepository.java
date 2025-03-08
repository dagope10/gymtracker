package com.app.gymtracker.repository;

import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.EjercicioDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioDiaRepository extends JpaRepository<EjercicioDia, Long> {
    List<EjercicioDia> findByDiaRutina(DiaRutina diaRutina);
    List<EjercicioDia> findByDiaRutinaIdOrderByOrdenAsc(Long diaRutinaId);
}