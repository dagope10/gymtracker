package com.app.gymtracker.service;

import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.Ejercicio;
import com.app.gymtracker.model.EjercicioDia;
import com.app.gymtracker.repository.DiaRutinaRepository;
import com.app.gymtracker.repository.EjercicioDiaRepository;
import com.app.gymtracker.repository.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EjercicioDiaService {

    private final EjercicioDiaRepository ejercicioDiaRepository;
    private final DiaRutinaRepository diaRutinaRepository;
    private final EjercicioRepository ejercicioRepository;

    @Autowired
    public EjercicioDiaService(
            EjercicioDiaRepository ejercicioDiaRepository,
            DiaRutinaRepository diaRutinaRepository,
            EjercicioRepository ejercicioRepository) {
        this.ejercicioDiaRepository = ejercicioDiaRepository;
        this.diaRutinaRepository = diaRutinaRepository;
        this.ejercicioRepository = ejercicioRepository;
    }

    /**
     * Añade un ejercicio a un día de rutina
     */
    @Transactional
    public EjercicioDia agregarEjercicioADia(Long diaId, Long ejercicioId, EjercicioDia ejercicioDia) {
        DiaRutina diaRutina = diaRutinaRepository.findById(diaId)
                .orElseThrow(() -> new RuntimeException("Día no encontrado con ID: " + diaId));
        
        Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejercicioId));
        
        ejercicioDia.setDiaRutina(diaRutina);
        ejercicioDia.setEjercicio(ejercicio);
        
        return ejercicioDiaRepository.save(ejercicioDia);
    }

    /**
     * Obtiene todos los ejercicios de un día ordenados por el campo orden
     */
    public List<EjercicioDia> obtenerEjerciciosPorDia(Long diaId) {
        return ejercicioDiaRepository.findByDiaRutinaIdOrderByOrdenAsc(diaId);
    }

    /**
     * Actualiza los detalles de un ejercicio en un día
     */
    @Transactional
    public EjercicioDia actualizarEjercicioDia(Long ejercicioDiaId, EjercicioDia detallesEjercicioDia) {
        EjercicioDia ejercicioDia = ejercicioDiaRepository.findById(ejercicioDiaId)
                .orElseThrow(() -> new RuntimeException("Relación ejercicio-día no encontrada con ID: " + ejercicioDiaId));
        
        ejercicioDia.setSeries(detallesEjercicioDia.getSeries());
        ejercicioDia.setRepeticiones(detallesEjercicioDia.getRepeticiones());
        ejercicioDia.setDescansoSegundos(detallesEjercicioDia.getDescansoSegundos());
        ejercicioDia.setOrden(detallesEjercicioDia.getOrden());
        ejercicioDia.setNotas(detallesEjercicioDia.getNotas());
        
        return ejercicioDiaRepository.save(ejercicioDia);
    }

    /**
     * Elimina un ejercicio de un día
     */
    @Transactional
    public void eliminarEjercicioDeDia(Long ejercicioDiaId) {
        ejercicioDiaRepository.deleteById(ejercicioDiaId);
    }
}