package com.matias.desafio_backend.desafio_backend.controller;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.excel.FillExcelFile;
import com.matias.desafio_backend.desafio_backend.xml.ReadXmlFile;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/*
* Esta clase tiene como finalidad definir un endpoint para poder leer un archivo
* XML y retornar un libro de excel que se aloja en las descargas
* */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class XmlToExcelController {

    private final ReadXmlFile readXmlFile;
    private final FillExcelFile fillExcelFile;

    /**
     * Metodo para crear el endpoint que se va a consumir para que nos devuelva el libro de excel
     *
     * @param file, este parametro representa los archivos que se pueden enviar en la consulta
     **/
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
