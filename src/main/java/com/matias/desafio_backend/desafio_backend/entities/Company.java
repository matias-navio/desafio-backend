package com.matias.desafio_backend.desafio_backend.entities;

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
public class Company {

    private Long nroContrato;

    private String cuit;
    private String denominacion;
    private String domicilio;
    private int codigoPostal;
    private LocalDateTime fechaDesdeNov;
    private LocalDateTime fechaHastaNov;
    private int organizador;
    private int productor;
    private int ciiu;

    private List<Movement> movements;

}
