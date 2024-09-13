package com.matias.desafio_backend.desafio_backend.xml;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadXmlFile {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ValidateCompanyData validateCompanyData;

    /*
    * Este metodo lee la ruta donde se encunetra el archivo XML y en base a eso
    * ejecuta el metodo que crea y lee el archivo (createFile) y el metodo que
    * guarda las empresas en la DB
    *
    * @Param filePath, es la ruta local donde esta el archivo XML */
    public void readAndSaveCompany(String filePath){

        List<Company> companies = createFile(filePath);
        saveCompaniesInDatabase(companies);
    }

    /*
    * Este metodo obtiene la lista de empresas del archivo XML y las guarda en la DB
    * validando que no existe mediante la direccion
    *
    * @Param companies, es la lista de empresas que llega del archivo*/
    @Transactional
    private void saveCompaniesInDatabase(List<Company> companies) {

        for (Company company : companies) {

            if (companyRepository.findByDomicilio(company.getDomicilio()).isEmpty())
                companyRepository.save(company);
        }
    }

    /*
    * Este metodo obtiene y lee el archivo XML, y en base a sus tags
    * crea la lista de empresas y movimientos
    *
    * @Param filePath, es la ruta donde se encuentra el archivo que vamos a leer*/
    private List<Company> createFile(String filePath){

        List<Company> companiesList = new ArrayList<>();

        try {

            // creamos una nueva instancia de archivo con la ruta obtenida en filePath
            File fileXml = new File(filePath);

            // esto nos permite leer y analizar archivos de tipo XML
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document documentXml = builder.parse(fileXml);

            // esto verifica que no haya inconsistencias en el archivo XML
            documentXml.getDocumentElement().normalize();

            // devuelve una lista con los nodos o tags que coincidan con Empresa
            NodeList nodeCompaniesList = documentXml.getElementsByTagName("Empresa");

            List<String> errorMessages = new ArrayList<>();

            // iteramos sobre la lista de empresas
            for (int i = 0; i < nodeCompaniesList.getLength(); i++){

                // a partir de la lista de nodos, obtenemos cada nodo de forma separada
                Node nodeCompany = nodeCompaniesList.item(i);

                if(nodeCompany.getNodeType() == Node.ELEMENT_NODE){
                    Element companyElement = (Element) nodeCompany;

                    // esto verifica si hay algun valor nulo que llegue del archivo XML y nos avisa
                    List<String> companyErrors = validateCompanyData.validateCompany(companyElement);
                    if (!companyErrors.isEmpty()) {
                        errorMessages.add("Error en la empresa " + (i + 1) + ": " + String.join(", ", companyErrors));
                    }

                    // contruyo la empresa con un Builder a partir de los tags obtenidos en el XML
                    Company newCompany = Company.builder()
                            .codigoPostal(Integer.parseInt(companyElement.getElementsByTagName("CodigoPostal")
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
                            .productor(Integer.parseInt(companyElement.getElementsByTagName("Productor")
                                    .item(0).getTextContent()))
                            .nroContrato(Integer.parseInt(companyElement.getElementsByTagName("NroContrato")
                                    .item(0).getTextContent()))
                            .build();

                    List<Movement> movements = new ArrayList<>();

                    // devuelve una lista con los nodos o tags que coincidan con Movimiento
                    NodeList movementsNodeList = companyElement.getElementsByTagName("Movimiento");

                    // itero sobre la lista de movimientos
                    for(int j = 0; j < movementsNodeList.getLength(); j++){
                        Element movementElement = (Element) movementsNodeList.item(j);

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

            if (!errorMessages.isEmpty()) {
                System.out.println("Validation Errors:");
                for (String errorMessage : errorMessages) {
                    System.out.println(errorMessage);
                }
            } else {
                System.out.println("Todos los datos son validos");
            }

        }catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // devuelve la lista de empresas para luego guardarlas en la DB
        return companiesList;
    }

}
