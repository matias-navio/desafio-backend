package com.matias.desafio_backend.desafio_backend.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

@Component
public class ValidateCompanyData {

    public List<String> validateCompany(Element company){

        List<String> errors = new ArrayList<>();

        String[] data = {
                getTagValue("CUIT", company), getTagValue("NroContrato", company),
                getTagValue("Denominacion", company), getTagValue("Domicilio", company),
                getTagValue("CodigoPostal", company), getTagValue("FechaDesdeNov", company),
                getTagValue("FechaHastaNov", company), getTagValue("Organizador", company),
                getTagValue("Productor", company), getTagValue("CIIU", company)
        };

        for (String date : data) {

            if (date == null) {
                errors.add("Verifique si hay algún valor nulo");
            }
        }

        return errors;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            Node node = nodeList.item(0);
            if (node != null && node.getFirstChild() != null) {
                return node.getFirstChild().getNodeValue();
            }
        }
        return null;
    }
}
