package com.matias.desafio_backend.desafio_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nroContrato;
    private String cuit;
    private String denominacion;
    private String domicilio;
    private String codigoPostal;
    private String fechaDesdeNov;
    private String fechaHastaNov;
    private String organizador;
    private String productor;
    private String ciiu;

    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Movement> movements;

}
