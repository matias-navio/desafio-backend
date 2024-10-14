package com.matias.desafio_backend.desafio_backend.controller;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.excel.FillExcelFile;
import com.matias.desafio_backend.desafio_backend.excel.StyleHeader;
import com.matias.desafio_backend.desafio_backend.xml.ReadXmlFile;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class XmlToExcelController {

    private final ReadXmlFile readXmlFile;
    private final FillExcelFile fillExcelFile;

    @PostMapping(value = "/convert-xml-to-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> convertXmlToExcel(@RequestParam("file") MultipartFile file) throws Exception {

        List<Company> companies = readXmlFile.createFile(file.getInputStream());

        Workbook workbook = new XSSFWorkbook();
        fillExcelFile.fillExcel(workbook, companies);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();

        // Obtener el archivo Excel en bytes
        byte[] excelBytes = out.toByteArray();
        out.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Empresas.xlsx");
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentLength(excelBytes.length);

        // Establecer los encabezados de respuesta
        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);

    }
}
