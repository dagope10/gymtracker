package com.app.gymtracker.dto;

import java.util.List;

public class RutinaCompletaDTO {
    private String nombre;
    private String descripcion;
    private List<DiaRutinaDTO> dias;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DiaRutinaDTO> getDias() {
        return dias;
    }

    public void setDias(List<DiaRutinaDTO> dias) {
        this.dias = dias;
    }

    public static class DiaRutinaDTO {
        private String nombre;
        private Integer numeroDia;
        private String notas;
        private List<EjercicioDiaDTO> ejercicios;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Integer getNumeroDia() {
            return numeroDia;
        }

        public void setNumeroDia(Integer numeroDia) {
            this.numeroDia = numeroDia;
        }

        public String getNotas() {
            return notas;
        }

        public void setNotas(String notas) {
            this.notas = notas;
        }

        public List<EjercicioDiaDTO> getEjercicios() {
            return ejercicios;
        }

        public void setEjercicios(List<EjercicioDiaDTO> ejercicios) {
            this.ejercicios = ejercicios;
        }
    }

    public static class EjercicioDiaDTO {
        // Puede ser null si estamos creando un nuevo ejercicio
        private Long ejercicioId;
        
        // Datos para crear un nuevo ejercicio si ejercicioId es null
        private EjercicioDTO nuevoEjercicio;
        
        // Datos de la relación
        private Integer series;
        private Integer repeticiones;
        private Integer descansoSegundos;
        private String peso;
        private String notas;
        private Integer orden;

        public Long getEjercicioId() {
            return ejercicioId;
        }

        public void setEjercicioId(Long ejercicioId) {
            this.ejercicioId = ejercicioId;
        }
        
        public EjercicioDTO getNuevoEjercicio() {
            return nuevoEjercicio;
        }
        
        public void setNuevoEjercicio(EjercicioDTO nuevoEjercicio) {
            this.nuevoEjercicio = nuevoEjercicio;
        }

        public Integer getSeries() {
            return series;
        }

        public void setSeries(Integer series) {
            this.series = series;
        }

        public Integer getRepeticiones() {
            return repeticiones;
        }

        public void setRepeticiones(Integer repeticiones) {
            this.repeticiones = repeticiones;
        }

        public Integer getDescansoSegundos() {
            return descansoSegundos;
        }

        public void setDescansoSegundos(Integer descansoSegundos) {
            this.descansoSegundos = descansoSegundos;
        }

        public String getPeso() {
            return peso;
        }

        public void setPeso(String peso) {
            this.peso = peso;
        }

        public String getNotas() {
            return notas;
        }

        public void setNotas(String notas) {
            this.notas = notas;
        }

        public Integer getOrden() {
            return orden;
        }

        public void setOrden(Integer orden) {
            this.orden = orden;
        }
    }
    
    public static class EjercicioDTO {
        private String nombre;
        private String descripcion;
        private String grupoMuscular;
        private String urlImagen;
        private String videoUrl;
        private String instrucciones;
        
        public String getNombre() {
            return nombre;
        }
        
        public void setNombre(String nombre) {
            this.nombre = nombre;
        }
        
        public String getDescripcion() {
            return descripcion;
        }
        
        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }
        
        public String getGrupoMuscular() {
            return grupoMuscular;
        }
        
        public void setGrupoMuscular(String grupoMuscular) {
            this.grupoMuscular = grupoMuscular;
        }
        
        public String getUrlImagen() {
            return urlImagen;
        }
        
        public void setUrlImagen(String urlImagen) {
            this.urlImagen = urlImagen;
        }
        
        public String getVideoUrl() {
            return videoUrl;
        }
        
        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
        
        public String getInstrucciones() {
            return instrucciones;
        }
        
        public void setInstrucciones(String instrucciones) {
            this.instrucciones = instrucciones;
        }
    }
}