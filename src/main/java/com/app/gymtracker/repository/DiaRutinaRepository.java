package com.app.gymtracker.repository;

import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.Rutina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaRutinaRepository extends JpaRepository<DiaRutina, Long> {
    List<DiaRutina> findByRutina(Rutina rutina);
    List<DiaRutina> findByRutinaIdOrderByNumeroDiaAsc(Long rutinaId);
}