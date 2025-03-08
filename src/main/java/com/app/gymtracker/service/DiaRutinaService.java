package com.app.gymtracker.service;

import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.Rutina;
import com.app.gymtracker.repository.DiaRutinaRepository;
import com.app.gymtracker.repository.RutinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiaRutinaService {

    private final DiaRutinaRepository diaRutinaRepository;
    private final RutinaRepository rutinaRepository;

    @Autowired
    public DiaRutinaService(DiaRutinaRepository diaRutinaRepository, RutinaRepository rutinaRepository) {
        this.diaRutinaRepository = diaRutinaRepository;
        this.rutinaRepository = rutinaRepository;
    }

    /**
     * Crea un nuevo día para una rutina
     */
    @Transactional
    public DiaRutina crearDiaRutina(Long rutinaId, DiaRutina diaRutina) {
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + rutinaId));
        
        diaRutina.setRutina(rutina);
        
        return diaRutinaRepository.save(diaRutina);
    }

    /**
     * Obtiene todos los días de una rutina ordenados por número de día
     */
    public List<DiaRutina> obtenerDiasPorRutina(Long rutinaId) {
        return diaRutinaRepository.findByRutinaIdOrderByNumeroDiaAsc(rutinaId);
    }

    /**
     * Obtiene un día específico por su ID
     */
    public DiaRutina obtenerDiaPorId(Long diaId) {
        return diaRutinaRepository.findById(diaId)
                .orElseThrow(() -> new RuntimeException("Día no encontrado con ID: " + diaId));
    }

    /**
     * Actualiza un día existente
     */
    @Transactional
    public DiaRutina actualizarDiaRutina(Long diaId, DiaRutina detallesDia) {
        DiaRutina diaRutina = diaRutinaRepository.findById(diaId)
                .orElseThrow(() -> new RuntimeException("Día no encontrado con ID: " + diaId));
        
        diaRutina.setNombre(detallesDia.getNombre());
        diaRutina.setNumeroDia(detallesDia.getNumeroDia());
        diaRutina.setNotas(detallesDia.getNotas());
        
        return diaRutinaRepository.save(diaRutina);
    }

    /**
     * Elimina un día
     */
    @Transactional
    public void eliminarDiaRutina(Long diaId) {
        diaRutinaRepository.deleteById(diaId);
    }
}
