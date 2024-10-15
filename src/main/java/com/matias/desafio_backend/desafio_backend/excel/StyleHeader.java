package com.matias.desafio_backend.desafio_backend.excel;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class StyleHeader {

    /**
     * Este metodo unicamente sirve para darle estilos a las cabeceras de las tablas
     *
     * @param workbook, este objeto sirve para settear estilso a ciertas celdas
     * @return devuelve las celdas con el estilo definido
     * */
    public CellStyle styleHeaderCell(Workbook workbook){

        // doy formato de fecha a las celdas de tipo fecha
        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeader.setBorderTop(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderBottom(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderLeft(BorderStyle.MEDIUM);
        cellStyleHeader.setBorderRight(BorderStyle.MEDIUM);

        return  cellStyleHeader;
    }
}
