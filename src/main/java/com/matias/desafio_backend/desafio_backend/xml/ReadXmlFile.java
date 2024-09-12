package com.matias.desafio_backend.desafio_backend.xml;

import com.matias.desafio_backend.desafio_backend.entities.Empresa;
import com.matias.desafio_backend.desafio_backend.entities.Movimientos;
import com.matias.desafio_backend.desafio_backend.repositories.EmpresaRepository;
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
    private EmpresaRepository empresaRepository;

    public void leerYGuardarEmpresas(String filePath){

        List<Empresa> empresas = createFile(filePath);
        guardarEmpresasEnBaseDeDatos(empresas);
    }

    private void guardarEmpresasEnBaseDeDatos(List<Empresa> empresas) {

        for (Empresa empresa : empresas) {

            if(empresaRepository.findByCuit(empresa.getCuit()).isEmpty())
                empresaRepository.save(empresa);

        }
    }

    private static List<Empresa> createFile(String filePath){

        List<Empresa> listaEmpresas = new ArrayList<>();

        try {

            File fileXml = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document documentXml = builder.parse(fileXml);

            documentXml.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + documentXml.getDocumentElement().getNodeName());

            NodeList empresas = documentXml.getElementsByTagName("Empresa");

            for (int i = 0; i < empresas.getLength(); i++){

                Node empresa = empresas.item(i);

                if(empresa.getNodeType() == Node.ELEMENT_NODE){
                    Element empresaElement = (Element) empresa;

                    // contruyo la empresa con un Builder a partir de los tags obtenidos en el XML
                    Empresa newEmpresa = Empresa.builder()
                            .codigoPostal(empresaElement.getElementsByTagName("NroContrato")
                                    .item(0).getTextContent())
                            .domicilio(empresaElement.getElementsByTagName("Domicilio")
                                    .item(0).getTextContent())
                            .organizador(empresaElement.getElementsByTagName("Organizador")
                                    .item(0).getTextContent())
                            .cuit(empresaElement.getElementsByTagName("CUIT")
                                    .item(0).getTextContent())
                            .ciiu(empresaElement.getElementsByTagName("CIIU")
                                    .item(0).getTextContent())
                            .fechaHastaNov(empresaElement.getElementsByTagName("FechaHastaNov")
                                    .item(0).getTextContent())
                            .fechaDesdeNov(empresaElement.getElementsByTagName("FechaDesdeNov")
                                    .item(0).getTextContent())
                            .denominacion(empresaElement.getElementsByTagName("Denominacion")
                                    .item(0).getTextContent())
                            .productor(empresaElement.getElementsByTagName("Productor")
                                    .item(0).getTextContent())
                            .nroContrato(Integer.parseInt(empresaElement.getElementsByTagName("NroContrato")
                                    .item(0).getTextContent()))
                            .build();

                    List<Movimientos> movimientos = new ArrayList<>();
                    NodeList movimientosNodeList = empresaElement.getElementsByTagName("Movimiento");

                    for(int j = 0; j < movimientosNodeList.getLength(); j++){
                        Element elementMovimineto = (Element) movimientosNodeList.item(j);

                        // contruyo el movimiento con un builder a partir de los tgs obtenidos del XML
                        Movimientos movimiento = Movimientos.builder()
                                .codigoMovimiento(Integer.parseInt(elementMovimineto
                                        .getElementsByTagName("CodigoMovimiento")
                                        .item(0).getTextContent()))
                                .concepto(elementMovimineto.getElementsByTagName("Concepto").item(0).getTextContent())
                                .importe(Double.parseDouble(elementMovimineto
                                        .getElementsByTagName("Importe")
                                        .item(0).getTextContent()))
                                .saldoCtaCte(Double.parseDouble(elementMovimineto
                                        .getElementsByTagName("SaldoCtaCte")
                                        .item(0).getTextContent()))
                                .tipo(elementMovimineto.getElementsByTagName("Tipo").item(0).getTextContent())
                                .build();

                        movimiento.setEmpresa(newEmpresa);
                        movimientos.add(movimiento);
                    }

                    newEmpresa.setMovimientos(movimientos);
                    listaEmpresas.add(newEmpresa);

                }
            }

        }catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return listaEmpresas;
    }
}
