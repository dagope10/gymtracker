package com.app.gymtracker.service;

import com.app.gymtracker.dto.RutinaCompletaDTO;
import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.Ejercicio;
import com.app.gymtracker.model.EjercicioDia;
import com.app.gymtracker.model.Rutina;
import com.app.gymtracker.model.Usuario;
import com.app.gymtracker.repository.DiaRutinaRepository;
import com.app.gymtracker.repository.EjercicioDiaRepository;
import com.app.gymtracker.repository.EjercicioRepository;
import com.app.gymtracker.repository.RutinaRepository;
import com.app.gymtracker.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RutinaService {

    private final RutinaRepository rutinaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DiaRutinaRepository diaRutinaRepository;
    private final EjercicioRepository ejercicioRepository;
    private final EjercicioDiaRepository ejercicioDiaRepository;

    @Autowired
    public RutinaService(RutinaRepository rutinaRepository, UsuarioRepository usuarioRepository, DiaRutinaRepository diaRutinaRepository, 
                        EjercicioRepository ejercicioRepository, EjercicioDiaRepository ejercicioDiaRepository) {
        this.rutinaRepository = rutinaRepository;
        this.usuarioRepository = usuarioRepository;
        this.diaRutinaRepository = diaRutinaRepository;
        this.ejercicioDiaRepository = ejercicioDiaRepository;
        this.ejercicioRepository = ejercicioRepository;
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
        Rutina rutina = rutinaRepository.findById(rutinaId)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada con ID: " + rutinaId));
        
                // Cargar días manualmente
        List<DiaRutina> dias = diaRutinaRepository.findByRutinaIdOrderByNumeroDiaAsc(rutinaId);
        Set<DiaRutina> diasSet = new HashSet<>(dias);
        rutina.setDias(diasSet);
        
        return rutina;
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

    @Transactional
    public Rutina crearRutinaCompleta(String username, RutinaCompletaDTO rutinaDTO) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + username));
        Rutina rutina = new Rutina();
        rutina.setNombre(rutinaDTO.getNombre());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        rutina.setFechaCreacion(LocalDateTime.now());
        rutina.setUsuario(usuario);

        rutina = rutinaRepository.save(rutina);

        List<DiaRutina> diasCreados = new ArrayList<>();

        for(RutinaCompletaDTO.DiaRutinaDTO diaDTO : rutinaDTO.getDias()) {
            DiaRutina dia = new DiaRutina();
            dia.setNombre(diaDTO.getNombre());
            dia.setNumeroDia(diaDTO.getNumeroDia());
            dia.setNotas(diaDTO.getNotas());
            dia.setRutina(rutina);

            dia = diaRutinaRepository.save(dia);
            diasCreados.add(dia);

            for (RutinaCompletaDTO.EjercicioDiaDTO ejercicioDTO : diaDTO.getEjercicios()) {
                Ejercicio ejercicio;

                if (ejercicioDTO.getEjercicioId() != null) {
                    ejercicio = ejercicioRepository.findById(ejercicioDTO.getEjercicioId()).orElseThrow(() -> new RuntimeException("Ejercicio no encontrado con id: " + ejercicioDTO));

                } else if (ejercicioDTO.getNuevoEjercicio() != null) {
                    RutinaCompletaDTO.EjercicioDTO nuevoEjercicioDTO = ejercicioDTO.getNuevoEjercicio();
                    ejercicio = new Ejercicio();
                    ejercicio.setNombre(nuevoEjercicioDTO.getNombre());
                    ejercicio.setDescripcion(nuevoEjercicioDTO.getDescripcion());
                    ejercicio.setGrupoMuscular(nuevoEjercicioDTO.getGrupoMuscular());
                    ejercicio.setUrlImagen(nuevoEjercicioDTO.getUrlImagen());
                    ejercicio.setVideoUrl(nuevoEjercicioDTO.getVideoUrl());
                    ejercicio.setInstrucciones(nuevoEjercicioDTO.getInstrucciones());
                } else {
                    throw new RuntimeException("Se debe proporcionar un ID de ejercicio existente o datos para un nuevo ejercicio");
                }

                EjercicioDia ejercicioDia = new EjercicioDia();
                ejercicioDia.setDiaRutina(dia);
                ejercicioDia.setEjercicio(ejercicio);
                ejercicioDia.setSeries(ejercicioDTO.getSeries());
                ejercicioDia.setRepeticiones(ejercicioDTO.getRepeticiones());
                ejercicioDia.setDescansoSegundos(ejercicioDTO.getDescansoSegundos());
                ejercicioDia.setPeso(ejercicioDTO.getPeso());
                ejercicioDia.setNotas(ejercicioDTO.getNotas());
                ejercicioDia.setOrden(ejercicioDTO.getOrden());

                ejercicioDiaRepository.save(ejercicioDia);

            }
        }

        return rutinaRepository.findByIdWithDiasAndEjercicios(rutina.getId()).orElseThrow(()-> new RuntimeException("Error al recuperar la rutina creada"));

    }
}