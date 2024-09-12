package com.matias.desafio_backend.desafio_backend.services;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.repositories.CompanyRepository;
import com.matias.desafio_backend.desafio_backend.repositories.MovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MovementsRepository movementsRepository;

    public List<Company> findAll(){

        return (List<Company>) companyRepository.findAll();
    }

    public List<Movement> getMovementsByCompanyId(Long companyId){

        return movementsRepository.findByCompanyId(companyId);
    }
}
