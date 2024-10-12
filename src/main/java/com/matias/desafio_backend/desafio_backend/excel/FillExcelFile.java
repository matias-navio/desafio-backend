package com.matias.desafio_backend.desafio_backend.excel;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class FillExcelFile {

    private final StyleHeader styleHeader;

    @Autowired
    public FillExcelFile(StyleHeader styleHeader) {
        this.styleHeader = styleHeader;
    }

    /*
    * Este metodo crea las hojas correspondientes del libro creado
    *
    * @Param workbook, es el libro que se crea de cero en CreateExcel,
    * el cual completo con información de las Empresas y los movimientos*/
    public void fillExcel(Workbook workbook, List<Company> companies) {

        // creo dos hoja dentro del libro con los nombres Empresas y Movement
        Sheet companySheet = workbook.createSheet("Empresas");
        Sheet movementsSheet = workbook.createSheet("Movimientos");

        // llanemos las dos hojas con los datos del XML
        fillCompanySheet(companySheet, companies, workbook);
        fillMovementSheet(movementsSheet, companies);

        try{

            // Obtén la ruta del escritorio
            String userHome = System.getProperty("user.home");
            String desktopPath = userHome + "\\Downloads";

            // Nombre del archivo
            String fileName = "Empresas.xlsx";

            // Crea la ruta completa
            FileOutputStream outputStream = new FileOutputStream(desktopPath + "/" + fileName);
            workbook.write(outputStream);
            outputStream.close();

            System.out.println("Excel creado con éxito en: " + desktopPath + "/" + fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    * Este metodo llena la hoja Empresas con la información obtenida del archivo XML que se le envia
    * Crea el encabezado con el nombre de los campos y las filas con los datos
    *
    * @Param companySheet, es un paramatro de tipo Sheet que lee la hoja que se envia para llenar
    * @Param companies, es la lista de empresas obtenida del XML
    */
    private void fillCompanySheet(Sheet companySheet, List<Company> companies, Workbook workbook){

        // crea la cabecera para la hoja de Empresas
        Row headerRowCompany = companySheet.createRow(0);

        // lista con los valores de la cabecera
        String[] companyHeaders = {"Nro Contrato", "CUIT",
                                    "Denominación", "Domicilio",
                                    "Código Postal", "Productor"};

        // Estilos para la cabecera
        CellStyle headerStyle = styleHeader.styleHeaderCell(workbook);

        for (int i = 0; i < companyHeaders.length; i++) {
            Cell cell = headerRowCompany.createCell(i);
            cell.setCellValue(companyHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowNum = 1;
        for (Company company : companies) {
            Row row = companySheet.createRow(rowNum++);
            row.createCell(0).setCellValue(company.getNroContrato());
            row.createCell(1).setCellValue(company.getCuit());
            row.createCell(2).setCellValue(company.getDenominacion());
            row.createCell(3).setCellValue(company.getDomicilio());
            row.createCell(4).setCellValue(company.getCodigoPostal());
            row.createCell(5).setCellValue(company.getProductor());
        }
    }

    public void fillMovementSheet(Sheet movementSheet, List<Company> companies){

        Row headerRow = movementSheet.createRow(0);
        headerRow.createCell(0).setCellValue("NroContrato");
        headerRow.createCell(1).setCellValue("SaldoCtaCte");
        headerRow.createCell(2).setCellValue("Concepto");
        headerRow.createCell(3).setCellValue("Importe");

        int rowNum = 1;
        for (Company company : companies){

            List<Movement> movements = company.getMovements();

            for (Movement movement : movements) {
                Row row = movementSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(company.getNroContrato());
                row.createCell(1).setCellValue(movement.getSaldoCtaCte());
                row.createCell(2).setCellValue(movement.getConcepto());
                row.createCell(3).setCellValue(movement.getImporte());
            }
        }
    }
}
