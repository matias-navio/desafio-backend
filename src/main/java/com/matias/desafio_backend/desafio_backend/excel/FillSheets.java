package com.matias.desafio_backend.desafio_backend.excel;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.services.CompanyService;
import com.matias.desafio_backend.desafio_backend.xml.ReadXmlFile;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FillSheets {

    // inyeccion del servicio de empresa
    @Autowired
    private CompanyService companyService;

    // inyeccion de los estilos para las celdas
    @Autowired
    private StyleHeader styleHeader;

    // inyeccion para obtener el XML de empresas
    @Autowired
    private ReadXmlFile readXmlFile;

    /*
     * Método que llena ambas hojas de la empresa y los movimientos.
     * Esto asegura que solo se lea el archivo XML una vez.
     *
     * @Param companySheet, es la hoja de las empresas la cual se llena con los campos de Company
     * @Param movementSheet, es la hoja de los movimientos la cual se llena con los datos de Movement
     */
    public void fillSheets(Sheet companySheet, Sheet movementSheet, Workbook workbook) {

        // utiliza la ruta para acceder al archivo XML y con el metodo creteFile hace el resto
        String path = "C:\\Users\\pc\\OneDrive\\Escritorio\\desafio-backend\\DesafioBackend.xml";

        // Lista de empresas obtenida del XML (solo se lee una vez)
        List<Company> companies = readXmlFile.createFile(path);

        // llena las hojas del archivo excel
        fillCompanySheet(companies, companySheet, workbook);
        fillMovementSheet(companies, movementSheet, workbook);
    }

    /*
     * Metodo que obtiene los datos de las empresas de la base de datos
     * para poder pasarlos al archivo excel
     *
     * @Param companySheet, es la hoja de las empresas la cual se llena con los campos de Company*/
    public void fillCompanySheet(List<Company> companies, Sheet companySheet, Workbook workbook){

        int rowNum = 0;
        Row headerRow = companySheet.createRow(rowNum++);

        // lista con todos los datos de la empresa, para poner en el header de la hoja
        String[] headers = {"N° Contrato", "CUIT", "Denominación",
                "Domicilio", "Codigo postal", "Productor"};

        // crea el header con los valores de la lista headers
        for (int i = 0; i < headers.length; i++) {

            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(styleHeader.styleHeaderCell(workbook));
            companySheet.autoSizeColumn(i);
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
            row.createCell(5).setCellValue(company.getProductor());
        }
    }

    /*
     * Este metodo llena la hoja de Movimientos con los valores que se obtienen de la DB
     *
     * @Param movementSheet, hoja de movimientos donde se van a poblar los datos*/
    public void fillMovementSheet(List<Company> companies, Sheet movementSheet, Workbook workbook){

        int rowNum = 0;
        Row headerRow = movementSheet.createRow(rowNum++);

        // esto lo que hace es darle estilo a las celdas que van a ser hipervinculos
        CreationHelper creationHelper = workbook.getCreationHelper();
        CellStyle hlinkStyle = workbook.createCellStyle();
        Font hlinkFont = workbook.createFont();
        hlinkFont.setUnderline(Font.U_SINGLE);
        hlinkFont.setColor(IndexedColors.BLUE.getIndex());
        hlinkStyle.setFont(hlinkFont);

        /*
         * Itero sobre las empresas para poder obetener los movimientos
         * de cada una de ellas, y asi poder llenar la hoja de excel
         * */
        for(Company company : companies){

            // devuelve todos los movimientos de la DB, obtenidos por el id de la empresa
            List<Movement> movements = companyService.getMovementsByCompanyId(company.getNroContrato());

            // lista con los datos de los movimientos, para poder hacer el header
            String[] headers = {"Nro Contrato", "Saldo Cta. Cte.", "Concepto", "Importe"};

            // itera sobre los headers de movimiento, le asigna estilos
            for(int i = 0; i < headers.length; i++){

                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(styleHeader.styleHeaderCell(workbook));
                movementSheet.autoSizeColumn(i);
            }

            /* itera sobre la lista de movimientos y crea una fila por cada uno
             * donde se llenaran con los valores obtenidos de la DB
             * */
            for(Movement movement : movements){

                Row row = movementSheet.createRow(rowNum++);

                // a las celdas de la columna 0 las crea como hipervinculo
                Cell cell0 = row.createCell(0);
                cell0.setCellValue(movement.getCompany().getNroContrato());
                Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.DOCUMENT);

                // se vinculan con la hoja empresas en este caso la columna A donde esta el NroContrato
                String address = "'Empresas'!A" + ((company.getNroContrato()) + 1);
                hyperlink.setAddress(address);
                cell0.setHyperlink(hyperlink);
                cell0.setCellStyle(hlinkStyle);

                row.createCell(1).setCellValue(movement.getSaldoCtaCte());
                row.createCell(2).setCellValue(movement.getConcepto());
                row.createCell(3).setCellValue(movement.getImporte());
            }
        }
    }
}
