package com.matias.desafio_backend.desafio_backend.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

// esta clase tiene como finalidad validar que company no tenga errores en sus tags
@Component
public class ValidateCompanyData {

    // lista con los tags de company
    private static final String[] REQUIRED_TAGS = {
            "CUIT", "NroContrato", "Denominacion", "Domicilio",
            "CodigoPostal", "FechaDesdeNov", "FechaHastaNov",
            "Organizador", "Productor", "CIIU"
    };

    /*
     * Metodo que recorre la lista de tags y verifica si alguno falta o si
     * alguno tiene valores nulos
     *
     * @Param company, es el Element que recibimos a la hora de leeer el XML
     * con los nodos
     * */
    public List<String> validateCompany(Element company){

        List<String> errors = new ArrayList<>();

        for (String tag : REQUIRED_TAGS) {
            String value = getTagValue(tag, company);
            if (value == null) {
                errors.add("Falta la etiqueta: <" + tag + ">");
            } else if (value.trim().isEmpty()) {
                errors.add("El valor de la etiqueta <" + tag + "> está vacío");
            }
        }

        return errors;
    }

    /*
    * Este metodo sirve para interpretar los tags del XML y validar que luego que no sean nulos
    *
    * @Param tag, es el nombre del tag
    * @Param element, es el elemnto nodo
    * */
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
