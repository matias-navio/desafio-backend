package com.matias.desafio_backend.desafio_backend.controllers;

import com.matias.desafio_backend.desafio_backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/all")
    public ResponseEntity<?> findAll(){

        if(companyService.findAll().isEmpty()){
            return ResponseEntity.ok(Map.of("message", "La lista de empresas está vacia"));
        }

        return ResponseEntity.ok(companyService.findAll());
    }

    @GetMapping("/{nroContrato}")
    public ResponseEntity<?> findOne(@PathVariable Long nroContrato){

        return ResponseEntity.ok(companyService.findByNroContrato(nroContrato));
    }

}
