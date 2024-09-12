package com.matias.desafio_backend.desafio_backend.repositories;

import com.matias.desafio_backend.desafio_backend.entities.Movement;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovementsRepository extends CrudRepository<Movement, Long> {

    List<Movement> findByCompanyId(Long companyId);
}
