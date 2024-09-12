package com.matias.desafio_backend.desafio_backend.repositories;

import com.matias.desafio_backend.desafio_backend.entities.Empresa;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EmpresaRepository extends CrudRepository<Empresa, Long> {

    Optional<Empresa> findByCuit(String cuit);
}
