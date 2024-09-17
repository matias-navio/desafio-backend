package com.matias.desafio_backend.desafio_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double saldoCtaCte;
    private String tipo;
    private int codigoMovimiento;
    private String concepto;
    private double importe;

    @ManyToOne
    @JoinColumn(name = "nro_contrato")
    @JsonIgnore
    private Company company;

}
