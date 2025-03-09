package com.app.gymtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@NoArgsConstructor
@JsonIgnoreProperties("rutina")
@EqualsAndHashCode(exclude = "rutina")
@AllArgsConstructor
@Entity
@Table(name = "dias_rutina")
public class DiaRutina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    
    private Integer numeroDia;

    private String notas;

    @ManyToOne
    @JoinColumn(name = "rutina_id")
    private Rutina rutina;

    @OneToMany(mappedBy = "diaRutina", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EjercicioDia> ejercicios = new HashSet<>();
    
}
