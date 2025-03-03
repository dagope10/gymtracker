package com.app.gymtracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RutinaController {
    @GetMapping("/api/rutinas")
    public String getRutinas() {
        return "Lista de rutinas para Heroku! (prueba)";
    }
} 