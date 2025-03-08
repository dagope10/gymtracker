// RutinaController.java
package com.app.gymtracker.controller;

import com.app.gymtracker.model.DiaRutina;
import com.app.gymtracker.model.EjercicioDia;
import com.app.gymtracker.model.Rutina;
import com.app.gymtracker.repository.UsuarioRepository;
import com.app.gymtracker.service.DiaRutinaService;
import com.app.gymtracker.service.EjercicioDiaService;
import com.app.gymtracker.service.RutinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rutinas")
public class RutinaController {

    private final RutinaService rutinaService;
    private final DiaRutinaService diaRutinaService;
    private final EjercicioDiaService ejercicioDiaService;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public RutinaController(
            RutinaService rutinaService,
            DiaRutinaService diaRutinaService,
            EjercicioDiaService ejercicioDiaService,
            UsuarioRepository usuarioRepository) {
        this.rutinaService = rutinaService;
        this.diaRutinaService = diaRutinaService;
        this.ejercicioDiaService = ejercicioDiaService;
        this.usuarioRepository = usuarioRepository;
    }

    // Endpoint para obtener todas las rutinas del usuario
    @GetMapping
    public ResponseEntity<List<Rutina>> obtenerRutinasUsuario() {
        // Obtener username del usuario autenticado
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        List<Rutina> rutinas = rutinaService.obtenerRutinasPorUsername(username);
        return ResponseEntity.ok(rutinas);
    }

    // Endpoint para obtener una rutina específica con sus días y ejercicios
    @GetMapping("/{rutinaId}")
    public ResponseEntity<Rutina> obtenerRutina(@PathVariable Long rutinaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Rutina rutina = rutinaService.obtenerRutinaPorId(rutinaId);
        
        // Verificar que la rutina pertenece al usuario actual
        if (!rutina.getUsuario().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        return ResponseEntity.ok(rutina);
    }

    // Endpoint para crear una nueva rutina
    @PostMapping
    public ResponseEntity<Rutina> crearRutina(@RequestBody Map<String, String> datos) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        Rutina nuevaRutina = new Rutina();
        nuevaRutina.setNombre(datos.get("nombre"));
        nuevaRutina.setDescripcion(datos.get("descripcion"));
        nuevaRutina.setFechaCreacion(LocalDateTime.now());
        
        Rutina rutinaCreada = rutinaService.crearRutina(username, nuevaRutina);
        return ResponseEntity.status(HttpStatus.CREATED).body(rutinaCreada);
    }

    // Endpoint para añadir un día a una rutina
    @PostMapping("/{rutinaId}/dias")
    public ResponseEntity<DiaRutina> agregarDiaARutina(
            @PathVariable Long rutinaId,
            @RequestBody Map<String, Object> datos) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Verificar que la rutina pertenece al usuario
        Rutina rutina = rutinaService.obtenerRutinaPorId(rutinaId);
        if (!rutina.getUsuario().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        DiaRutina nuevoDia = new DiaRutina();
        nuevoDia.setNombre((String) datos.get("nombre"));
        nuevoDia.setNumeroDia(toInteger(datos.get("numeroDia")));
        nuevoDia.setNotas((String) datos.get("notas"));
        
        DiaRutina diaCreado = diaRutinaService.crearDiaRutina(rutinaId, nuevoDia);
        return ResponseEntity.status(HttpStatus.CREATED).body(diaCreado);
    }

    // Endpoint para añadir un ejercicio a un día
    @PostMapping("/dias/{diaId}/ejercicios")
    public ResponseEntity<EjercicioDia> agregarEjercicioADia(
            @PathVariable Long diaId,
            @RequestBody Map<String, Object> datos) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Verificar que el día pertenece a una rutina del usuario
        DiaRutina dia = diaRutinaService.obtenerDiaPorId(diaId);
        if (!dia.getRutina().getUsuario().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        EjercicioDia nuevoEjercicioDia = new EjercicioDia();
        nuevoEjercicioDia.setOrden((Integer) datos.get("orden"));
        nuevoEjercicioDia.setSeries((Integer) datos.get("series"));
        nuevoEjercicioDia.setRepeticiones((Integer) datos.get("repeticiones"));
        nuevoEjercicioDia.setDescansoSegundos((Integer) datos.get("descansoSegundos"));
        nuevoEjercicioDia.setNotas((String) datos.get("notas"));
        
        Long ejercicioId = Long.valueOf(datos.get("ejercicioId").toString());
        
        EjercicioDia ejercicioDiaCreado = ejercicioDiaService.agregarEjercicioADia(diaId, ejercicioId, nuevoEjercicioDia);
        return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioDiaCreado);
    }

    // Endpoint para actualizar una rutina
    @PutMapping("/{rutinaId}")
    public ResponseEntity<Rutina> actualizarRutina(
            @PathVariable Long rutinaId,
            @RequestBody Map<String, String> datos) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Verificar que la rutina pertenece al usuario
        Rutina rutina = rutinaService.obtenerRutinaPorId(rutinaId);
        if (!rutina.getUsuario().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        rutina.setNombre(datos.get("nombre"));
        rutina.setDescripcion(datos.get("descripcion"));
        
        Rutina rutinaActualizada = rutinaService.actualizarRutina(rutinaId, rutina);
        return ResponseEntity.ok(rutinaActualizada);
    }

    // Endpoint para eliminar una rutina
    @DeleteMapping("/{rutinaId}")
    public ResponseEntity<?> eliminarRutina(@PathVariable Long rutinaId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        
        // Verificar que la rutina pertenece al usuario
        Rutina rutina = rutinaService.obtenerRutinaPorId(rutinaId);
        if (!rutina.getUsuario().getUsername().equals(username)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        
        rutinaService.eliminarRutina(rutinaId);
        return ResponseEntity.noContent().build();
    }

    private Integer toInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        return Integer.parseInt(value.toString());
    }
}
