package com.matias.desafio_backend.desafio_backend.excel;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.services.CompanyService;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Component
public class FillExcelFile {

    @Autowired
    private final CompanyService companyService;

    public FillExcelFile(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void fillExcel(Workbook workbook){

        // creo dos hoja dentro del libro con los nombres Empresas y Movement
        Sheet companySheet = workbook.createSheet("Empresas");
        Sheet hojaMovimientos = workbook.createSheet("Movimientos");

        List<Company> companies = companyService.findAll();

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());

        int rowNum = 0;
        Row headerRow = companySheet.createRow(rowNum++);

        String[] headers = {"NroContrato", "CUIT", "Denominacion",
                "Domicilio", "CodigoPostal", "FechaDesdeNov",
                "FechaHastaNov", "Organizador", "Productor", "CIIU"};

        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
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

        try {

            OutputStream output = new FileOutputStream("Empresas.xlsx");
            workbook.write(output);

            System.out.println("Excel creado con exito");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
