package com.matias.desafio_backend.desafio_backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private Long nroContrato;

    private String cuit;
    private String denominacion;
    private String domicilio;
    private int codigoPostal;
    private LocalDateTime fechaDesdeNov;
    private LocalDateTime fechaHastaNov;
    private int organizador;
    private String productor;
    private int ciiu;

    @OneToMany(mappedBy = "company",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true)
    private List<Movement> movements;

}
