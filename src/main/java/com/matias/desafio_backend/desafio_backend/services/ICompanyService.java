package com.matias.desafio_backend.desafio_backend.services;

import com.matias.desafio_backend.desafio_backend.entities.Company;

import java.util.List;

public interface ICompanyService {

    List<Company> findAll();

    Company findByNroContrato(Long nroContrato);
}
