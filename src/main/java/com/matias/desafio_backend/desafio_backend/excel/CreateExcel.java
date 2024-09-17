package com.matias.desafio_backend.desafio_backend.excel;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateExcel implements CommandLineRunner {

    private final FillExcelFile fillExcelFile;

    public CreateExcel(FillExcelFile fillExcelFile) {
        this.fillExcelFile = fillExcelFile;
    }

    @Override
    public void run(String... args) throws Exception {

        /* Creamos un libro en blanco en excel que se guarda en memoria
         *
         * Con XSSWorkbook se crea con extencion xlsx
         * */
        XSSFWorkbook workbook = new XSSFWorkbook();

        fillExcelFile.fillExcel(workbook);

    }
}
