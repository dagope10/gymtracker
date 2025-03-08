package com.app.gymtracker.service;

import com.app.gymtracker.model.Ejercicio;
import com.app.gymtracker.repository.EjercicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    @Autowired
    public EjercicioService(EjercicioRepository ejercicioRepository) {
        this.ejercicioRepository = ejercicioRepository;
    }

    /**
     * Crea un nuevo ejercicio
     */
    @Transactional
    public Ejercicio crearEjercicio(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    /**
     * Busca ejercicios por nombre
     */
    public List<Ejercicio> buscarEjerciciosPorNombre(String nombre) {
        return ejercicioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    /**
     * Obtiene ejercicios por grupo muscular
     */
    public List<Ejercicio> obtenerEjerciciosPorGrupoMuscular(String grupoMuscular) {
        return ejercicioRepository.findByGrupoMuscular(grupoMuscular);
    }

    /**
     * Obtiene todos los ejercicios
     */
    public List<Ejercicio> obtenerTodosLosEjercicios() {
        return ejercicioRepository.findAll();
    }

    /**
     * Obtiene un ejercicio por su ID
     */
    public Ejercicio obtenerEjercicioPorId(Long ejercicioId) {
        return ejercicioRepository.findById(ejercicioId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejercicioId));
    }

    /**
     * Actualiza un ejercicio existente
     */
    @Transactional
    public Ejercicio actualizarEjercicio(Long ejercicioId, Ejercicio detallesEjercicio) {
        Ejercicio ejercicio = ejercicioRepository.findById(ejercicioId)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con ID: " + ejercicioId));
        
        ejercicio.setNombre(detallesEjercicio.getNombre());
        ejercicio.setDescripcion(detallesEjercicio.getDescripcion());
        ejercicio.setGrupoMuscular(detallesEjercicio.getGrupoMuscular());
        ejercicio.setUrlImagen(detallesEjercicio.getUrlImagen());
        ejercicio.setVideoUrl(detallesEjercicio.getVideoUrl());
        ejercicio.setInstrucciones(detallesEjercicio.getInstrucciones());
        
        return ejercicioRepository.save(ejercicio);
    }
}