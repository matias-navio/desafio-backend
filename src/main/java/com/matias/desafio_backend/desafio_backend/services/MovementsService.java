package com.matias.desafio_backend.desafio_backend.services;

import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.repositories.MovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovementsService implements IMovementService{

    @Autowired
    private MovementsRepository movementsRepository;

    public List<Movement> findAll(){

        return (List<Movement>) movementsRepository.findAll();
    }
}
