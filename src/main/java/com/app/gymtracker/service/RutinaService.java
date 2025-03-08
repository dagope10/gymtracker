package com.app.gymtracker.service;

import com.app.gymtracker.model.Rutina;
import com.app.gymtracker.model.Usuario;
import com.app.gymtracker.repository.RutinaRepository;
import com.app.gymtracker.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RutinaService(RutinaRepository rutinaRepository, UsuarioRepository usuarioRepository) {
        this.rutinaRepository = rutinaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Crea una nueva rutina para un usuario
     */
    @Transactional
    public Rutina crearRutina(String username, Rutina rutina) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        rutina.setUsuario(usuario);
        rutina.setFechaCreacion(LocalDateTime.now());
        
        return rutinaRepository.save(rutina);
    }

    /**
     * Obtiene todas las rutinas de un usuario por su username
     */
    public List<Rutina> obtenerRutinasPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        
        return rutinaRepository.findByUsuario(usuario);
    }

    /**
     * Obtiene una rutina por su ID
     */
    public Rutina obtenerRutinaPorId(Long rutinaId) {
        return rutinaRepository.findByIdWithDiasAndEjercicios(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + rutinaId));
    }

    /**
     * Actualiza una rutina existente
     */
    @Transactional
    public Rutina actualizarRutina(Long rutinaId, Rutina detallesRutina) {
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + rutinaId));
        
        rutina.setNombre(detallesRutina.getNombre());
        rutina.setDescripcion(detallesRutina.getDescripcion());
        
        return rutinaRepository.save(rutina);
    }

    /**
     * Elimina una rutina
     */
    @Transactional
    public void eliminarRutina(Long rutinaId) {
        rutinaRepository.deleteById(rutinaId);
    }
}