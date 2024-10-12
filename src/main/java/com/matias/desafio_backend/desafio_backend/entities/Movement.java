package com.matias.desafio_backend.desafio_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {

    private Long id;

    private double saldoCtaCte;
    private String tipo;
    private int codigoMovimiento;
    private String concepto;
    private double importe;

    private Company company;

}
