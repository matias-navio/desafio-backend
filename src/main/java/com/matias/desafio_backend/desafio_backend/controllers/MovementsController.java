package com.matias.desafio_backend.desafio_backend.controllers;

import com.matias.desafio_backend.desafio_backend.services.MovementsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/movements")
public class MovementsController {

    @Autowired
    private MovementsService movementsService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){

        if(movementsService.findAll().isEmpty()){
            return ResponseEntity.ok(Map.of("message", "La lista de movimientos está vacia"));
        }

        return ResponseEntity.ok(movementsService.findAll());
    }
}
