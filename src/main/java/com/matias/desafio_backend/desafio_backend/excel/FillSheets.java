package com.matias.desafio_backend.desafio_backend.excel;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.services.CompanyService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
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
    public void fillCompanySheet(Sheet companySheet, Workbook workbook){

        // con esto permito darte formato y tipo de dato a las celdas
        DataFormat format = workbook.createDataFormat();

        // doy formato de fecha a las celdas de tipo fecha
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format.getFormat("d-M-yyy hh:mm"));

        // lista de empresas obtenida de la DB
        List<Company> companies = companyService.findAll();

        int rowNum = 0;
        Row headerRow = companySheet.createRow(rowNum++);

        // lista con todos los datos de la empresa, para poner en el header de la hoja
        String[] headers = {"N° Contrato", "CUIT", "Denominación",
                "Domicilio", "Codigo postal", "Fecha desde nov.",
                "Fecha hasta nov.", "Organizador", "Productor", "CIIU"};

        // crea el header con los valores de la lista headers
        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        /*
        * itera sobre la lista de empresas y crea una fila por cada una
        * en la que completa con los datos obtenidos de la DB
        **/
        for (Company company : companies){

            Row row = companySheet.createRow(rowNum++);
            row.createCell(0).setCellValue(company.getNroContrato());
            row.createCell(1).setCellValue("'" + company.getCuit());
            row.createCell(2).setCellValue(company.getDenominacion());
            row.createCell(3).setCellValue(company.getDomicilio());
            row.createCell(4).setCellValue(company.getCodigoPostal());
            if(company.getFechaDesdeNov() != null){
                Cell cellDesde = row.createCell(5);
                cellDesde.setCellValue(company.getFechaDesdeNov()
                        .format(DateTimeFormatter.ofPattern("d-M-yyyy hh:mm")));
                cellDesde.setCellStyle(cellStyle);
            }
            if(company.getFechaHastaNov() != null){
                Cell cellDesde = row.createCell(6);
                cellDesde.setCellValue(company.getFechaHastaNov()
                        .format(DateTimeFormatter.ofPattern("d-M-yyyy hh:mm")));
                cellDesde.setCellStyle(cellStyle);
            }
            row.createCell(7).setCellValue(company.getOrganizador());
            row.createCell(8).setCellValue(company.getProductor());
            row.createCell(9).setCellValue(company.getCiiu());
        }
    }

    /*
    * Este metodo llena la hoja de Movimientos con los valores que se obtienen de la DB
    *
    * @Param movementSheet, hoja de movimientos donde se van a poblar los datos*/
    public void fillMovementSheet(Sheet movementSheet){

        // lista de empresas de la DB
        List<Company> companies = companyService.findAll();

        int rowNum = 0;
        Row headerRow = movementSheet.createRow(rowNum++);

        /*
        * Itero sobre las empresas para poder obetener los movimientos
        * de cada una de ellas, y asi poder llenar la hoja de excel
        * */
        for(Company company : companies){

            // devuelve todos los movimientos de la DB, obtenidos por el id de la empresa
            List<Movement> movements = companyService.getMovementsByCompanyId(company.getId());

            // lista con los datos de los movimientos, para poder hacer el header
            String[] headers = {"Saldo Cta. Cte.", "Tipo", "Cod. movimiento",
                    "Concepto", "Importe", "Empresa_id"};

            for(int i = 0; i < headers.length; i++){

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            /* itera sobre la lista de movimientos y crea una fila por cada uno
            * donde se llenaran con los valores obtenidos de la DB
            * */
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
