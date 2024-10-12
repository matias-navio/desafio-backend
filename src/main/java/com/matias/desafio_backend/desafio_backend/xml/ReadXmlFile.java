package com.matias.desafio_backend.desafio_backend.xml;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadXmlFile {

    @Autowired
    private ValidateCompanyData validateCompanyData;

    @Autowired
    private ValidateMovements validateMovements;

    /*
     * Este metodo obtiene y lee el archivo XML
     *
     * @Param filePath, es la ruta donde se encuentra el archivo que vamos a leer
    */
    public List<Company> createFile(InputStream inputStream){

        List<Company> companies;

        try {

            // esto nos permite leer y analizar archivos de tipo XML
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document documentXml = builder.parse(inputStream);

            // esto verifica que no haya inconsistencias en el archivo XML
            documentXml.getDocumentElement().normalize();

            // devuelve una lista con los nodos o tags que coincidan con Empresa
            NodeList nodeCompaniesList = documentXml.getElementsByTagName("Empresa");

            companies = companyList(nodeCompaniesList);

        }catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return companies;
    }

    /*
    * Metodo para devolver la lista de empresas del XML, tambien las guarda en DB
    * y avisa en caso de errores
    *
    * @Param nodeCompaniesList, usa el nodo del XML para obtener la lista de empresas*/
    public List<Company> companyList(NodeList nodeCompaniesList){

        List<Company> companiesList = new ArrayList<>();
        List<String> errorsMessage = new ArrayList<>();

        // iteramos sobre la lista de empresas
        for (int i = 0; i < nodeCompaniesList.getLength(); i++){

            // a partir de la lista de nodos, obtenemos cada nodo de forma separada
            Node nodeCompany = nodeCompaniesList.item(i);

            if(nodeCompany.getNodeType() == Node.ELEMENT_NODE){
                Element companyElement = (Element) nodeCompany;

                // Validación de empresa
                List<String> companyErrors = validateCompanyData.validateCompany(companyElement);
                if (!companyErrors.isEmpty()) {
                    errorsMessage.add("Error en la empresa " + (i + 1) + ": " + String.join(", ", companyErrors));
                    continue;
                }

                // contruyo la empresa con un Builder a partir de los tags obtenidos en el XML
                Company newCompany = Company.builder()
                        .nroContrato(Long.parseLong(companyElement.getElementsByTagName("NroContrato")
                                .item(0).getTextContent()))
                        .domicilio(companyElement.getElementsByTagName("Domicilio")
                                .item(0).getTextContent())
                        .organizador(Integer.parseInt(companyElement.getElementsByTagName("Organizador")
                                .item(0).getTextContent()))
                        .cuit(companyElement.getElementsByTagName("CUIT")
                                .item(0).getTextContent())
                        .ciiu(Integer.parseInt(companyElement.getElementsByTagName("CIIU")
                                .item(0).getTextContent()))
                        .fechaHastaNov(LocalDateTime.parse(companyElement.getElementsByTagName("FechaHastaNov")
                                .item(0).getTextContent()))
                        .fechaDesdeNov(LocalDateTime.parse(companyElement.getElementsByTagName("FechaDesdeNov")
                                .item(0).getTextContent()))
                        .denominacion(companyElement.getElementsByTagName("Denominacion")
                                .item(0).getTextContent())
                        .productor(companyElement.getElementsByTagName("Productor")
                                .item(0).getTextContent())
                        .codigoPostal(Integer.parseInt(companyElement.getElementsByTagName("CodigoPostal")
                                .item(0).getTextContent()))
                        .build();

                List<Movement> movements = new ArrayList<>();

                // devuelve una lista con los nodos o tags que coincidan con Movimiento
                NodeList movementsNodeList = companyElement.getElementsByTagName("Movimiento");

                // itero sobre la lista de movimientos
                for(int j = 0; j < movementsNodeList.getLength(); j++){
                    Element movementElement = (Element) movementsNodeList.item(j);

                    // Validar movimiento
                    List<String> movementsErrors = validateMovements.validateMovements(movementElement);
                    if (!movementsErrors.isEmpty()) {
                        errorsMessage.add("Error en el movimiento de la empresa " + (i + 1) + ": " + String.join(", ", movementsErrors));
                        continue; // Saltar este movimiento en caso de errores
                    }

                    // contruyo el movimiento con un builder a partir de los tgs obtenidos del XML
                    Movement newMovement = Movement.builder()
                            .codigoMovimiento(Integer.parseInt(movementElement
                                    .getElementsByTagName("CodigoMovimiento")
                                    .item(0).getTextContent()))
                            .concepto(movementElement.getElementsByTagName("Concepto").item(0).getTextContent())
                            .importe(Double.parseDouble(movementElement
                                    .getElementsByTagName("Importe")
                                    .item(0).getTextContent()))
                            .saldoCtaCte(Double.parseDouble(movementElement
                                    .getElementsByTagName("SaldoCtaCte")
                                    .item(0).getTextContent()))
                            .tipo(movementElement.getElementsByTagName("Tipo").item(0).getTextContent())
                            .build();

                    newMovement.setCompany(newCompany);
                    movements.add(newMovement);
                }

                newCompany.setMovements(movements);
                companiesList.add(newCompany);

            }
        }

        // verifica si hay errore, si hay avisa con un msj, si no hay tambien avisa pero no corta el flujo
        if (!errorsMessage.isEmpty()) {
            for (String error : errorsMessage) {
                System.out.println("Validación de errores: " + error);
            }
        } else {
            System.out.println("Todos los valores son válidos.");
        }

        // devuelve la lista de empresas para luego guardarlas en la DB
        return companiesList;
    }
}
