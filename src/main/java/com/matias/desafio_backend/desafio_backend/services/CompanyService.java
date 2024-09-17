package com.matias.desafio_backend.desafio_backend.services;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.repositories.CompanyRepository;
import com.matias.desafio_backend.desafio_backend.repositories.MovementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompanyService implements ICompanyService{

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MovementsRepository movementsRepository;

    // devueldo la lista  de todas las empreas
    @Transactional
    public List<Company> findAll(){

        return (List<Company>) companyRepository.findAll();
    }

    @Override
    public Company findByNroContrato(Long nroContrato) {

        // aca se puede hacer una excepcion personalizada para que devuelva un error mas amigable
        return companyRepository.findByNroContrato(nroContrato)
                .orElseThrow(() -> new RuntimeException("No se encontró la empresa"));
    }

    // devuelve los movimientos de cada empresa dependiendo deL ID
    @Transactional
    public List<Movement> getMovementsByCompanyId(Long nroContrato){

        return movementsRepository.findByCompanyNroContrato(nroContrato);
    }
}
