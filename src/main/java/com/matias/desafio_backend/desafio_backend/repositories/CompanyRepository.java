package com.matias.desafio_backend.desafio_backend.repositories;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company, Long> {

    Optional<Company> findByNroContrato(Long nroContrato);
}
