package com.matias.desafio_backend.desafio_backend.excel;

import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Component
public class FillExcelFile {

    @Autowired
    private FillSheets fillSheets;

    /*
    * Este metodo crea las hojas correspondientes del libro creado
    *
    * @Param workbook, es el libro que se crea de cero en CreateExcel,
    * el cual completo con información de las Empresas y los movimientos*/
    public void fillExcel(Workbook workbook) {

        // creo dos hoja dentro del libro con los nombres Empresas y Movement
        Sheet companySheet = workbook.createSheet("Empresas");
        Sheet movementsSheet = workbook.createSheet("Movimientos");

        // llamada al metodo que llena la hoja de las empresas
        fillSheets.fillCompanySheet(companySheet, workbook);

        // llamada al metodo que llena la hoja de movimientos
        fillSheets.fillMovementSheet(movementsSheet);

        try {

            OutputStream output = new FileOutputStream("Empresas.xlsx");
            workbook.write(output);

            System.out.println("Excel creado con exito");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
