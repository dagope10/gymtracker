package com.app.gymtracker.repository;

import com.app.gymtracker.model.Rutina;
import com.app.gymtracker.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutinaRepository extends JpaRepository<Rutina, Long> {
    List<Rutina> findByUsuario(Usuario usuario);
    List<Rutina> findByUsuarioId(Long usuarioId);
    List<Rutina> findByUsuarioUsernameOrderByFechaCreacionDesc(String username);

    @Query("SELECT r FROM Rutina r LEFT JOIN FETCH r.dias d LEFT JOIN FETCH d.ejercicios WHERE r.id = :rutinaId")
    Optional<Rutina> findByIdWithDiasAndEjercicios(@Param("rutinaId") Long rutinaId);
}