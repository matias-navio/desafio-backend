package com.matias.desafio_backend.desafio_backend.services;

import com.matias.desafio_backend.desafio_backend.entities.Movement;

import java.util.List;

public interface IMovementService {

    List<Movement> findAll();
}
