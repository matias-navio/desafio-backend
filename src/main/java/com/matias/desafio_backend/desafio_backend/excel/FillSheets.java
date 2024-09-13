package com.matias.desafio_backend.desafio_backend.excel;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.services.CompanyService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FillSheets {

    @Autowired
    private CompanyService companyService;

    /*
     * Metodo que obtiene los datos de las empresas de la base de datos
     * para poder pasarlos al archivo excel
     *
     * @Param companySheet, es la hoja de las empresas la cual se llena con los campos de Company*/
    public void fillCompanySheet(Sheet companySheet){

        List<Company> companies = companyService.findAll();

        int rowNum = 0;
        Row headerRow = companySheet.createRow(rowNum++);

        String[] headers = {"N° Contrato", "CUIT", "Denominación",
                "Domicilio", "Codigo postal", "Fecha desde nov.",
                "Fecha hasta nov.", "Organizador", "Productor", "CIIU"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        for (Company company : companies){
            Row row = companySheet.createRow(rowNum++);
            row.createCell(0).setCellValue(company.getNroContrato());
            row.createCell(1).setCellValue(company.getCuit());
            row.createCell(2).setCellValue(company.getDenominacion());
            row.createCell(3).setCellValue(company.getDomicilio());
            row.createCell(4).setCellValue(company.getCodigoPostal());
            row.createCell(5).setCellValue(company.getFechaDesdeNov());
            row.createCell(6).setCellValue(company.getFechaHastaNov());
            row.createCell(7).setCellValue(company.getOrganizador());
            row.createCell(8).setCellValue(company.getProductor());
            row.createCell(9).setCellValue(company.getCiiu());
        }
    }

    public void fillMovementSheet(Sheet movementSheet){

        List<Company> companies = companyService.findAll();

        int rowNum = 0;
        Row headerRow = movementSheet.createRow(rowNum++);

        for(Company company : companies){

            List<Movement> movements = companyService.getMovementsByCompanyId(company.getId());

            String[] headers = {"Saldo Cta. Cte.", "Tipo", "Cod. movimiento",
                    "Concepto", "Importe", "Empresa_id"};

            for(int i = 0; i < headers.length; i++){

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for(Movement movement : movements){

                Row row = movementSheet.createRow(rowNum++);
                row.createCell(0).setCellValue(movement.getSaldoCtaCte());
                row.createCell(1).setCellValue(movement.getTipo());
                row.createCell(2).setCellValue(movement.getCodigoMovimiento());
                row.createCell(3).setCellValue(movement.getConcepto());
                row.createCell(4).setCellValue(movement.getImporte());
                row.createCell(5).setCellValue(movement.getCompany().getId());
            }
        }
    }
}
