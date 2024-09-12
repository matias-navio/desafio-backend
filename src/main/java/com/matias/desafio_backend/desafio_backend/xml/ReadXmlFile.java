package com.matias.desafio_backend.desafio_backend.xml;

import com.matias.desafio_backend.desafio_backend.entities.Company;
import com.matias.desafio_backend.desafio_backend.entities.Movement;
import com.matias.desafio_backend.desafio_backend.repositories.CompanyRepository;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReadXmlFile {

    @Autowired
    private CompanyRepository companyRepository;

    public void readAndSveCompany(String filePath){

        List<Company> companies = createFile(filePath);
        saveCompaniesInDatabase(companies);
    }

    private void saveCompaniesInDatabase(List<Company> companies) {

        for (Company company : companies) {

            if(companyRepository.findByCuit(company.getCuit()).isEmpty())
                companyRepository.save(company);

        }
    }

    private static List<Company> createFile(String filePath){

        List<Company> companiesList = new ArrayList<>();

        try {

            File fileXml = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document documentXml = builder.parse(fileXml);

            documentXml.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + documentXml.getDocumentElement().getNodeName());

            NodeList nodeCompaniesList = documentXml.getElementsByTagName("Empresa");

            for (int i = 0; i < nodeCompaniesList.getLength(); i++){

                Node nodeCompany = nodeCompaniesList.item(i);

                if(nodeCompany.getNodeType() == Node.ELEMENT_NODE){
                    Element companyElement = (Element) nodeCompany;

                    // contruyo la empresa con un Builder a partir de los tags obtenidos en el XML
                    Company newCompany = Company.builder()
                            .codigoPostal(companyElement.getElementsByTagName("NroContrato")
                                    .item(0).getTextContent())
                            .domicilio(companyElement.getElementsByTagName("Domicilio")
                                    .item(0).getTextContent())
                            .organizador(companyElement.getElementsByTagName("Organizador")
                                    .item(0).getTextContent())
                            .cuit(companyElement.getElementsByTagName("CUIT")
                                    .item(0).getTextContent())
                            .ciiu(companyElement.getElementsByTagName("CIIU")
                                    .item(0).getTextContent())
                            .fechaHastaNov(companyElement.getElementsByTagName("FechaHastaNov")
                                    .item(0).getTextContent())
                            .fechaDesdeNov(companyElement.getElementsByTagName("FechaDesdeNov")
                                    .item(0).getTextContent())
                            .denominacion(companyElement.getElementsByTagName("Denominacion")
                                    .item(0).getTextContent())
                            .productor(companyElement.getElementsByTagName("Productor")
                                    .item(0).getTextContent())
                            .nroContrato(Integer.parseInt(companyElement.getElementsByTagName("NroContrato")
                                    .item(0).getTextContent()))
                            .build();

                    List<Movement> movements = new ArrayList<>();
                    NodeList movementsNodeList = companyElement.getElementsByTagName("Movimiento");

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

        }catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return companiesList;
    }
}
