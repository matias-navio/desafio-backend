package com.matias.desafio_backend.desafio_backend.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

// clase que tiene como finalidad, validar que los tags de movimiento no tengan errores
@Component
public class ValidateMovements {

    // lista con los tags de movimiento
    private static final String[] REQUIRED_TAGS = {
            "SaldoCtaCte", "Tipo", "CodigoMovimiento", "Concepto",
            "Importe"
    };

    /**
    * Metodo que recorre la lista de tags y verifica si alguno falta o si
    * alguno tiene valores nulos
    *
    * @param movement, es el Element que recibimos a la hora de leeer el XML
    * con los nodos
     * @return errors, devuelve una lista de String con los errores encontrados
    * */
    public List<String> validateMovements(Element movement){

        List<String> errors = new ArrayList<>();

        for (String tag : REQUIRED_TAGS) {
            try {
                String value = getTagValue(tag, movement);

                // Si la etiqueta no existe o el valor es nulo
                if (value == null) {
                    errors.add("Falta la etiqueta: <" + tag + ">");
                } else if (value.trim().isEmpty()) {
                    errors.add("El valor de la etiqueta <" + tag + "> está vacío");
                }
            } catch (Exception e) {
                errors.add("Error al procesar la etiqueta: <" + tag + "> - " + e.getMessage());
            }
        }

        return errors;
    }

    /**
     * Este metodo sirve para interpretar los tags del XML y validar que luego que no sean nulos
     *
     * @param tag, es el nombre del tag
     * @param element, es el elemnto nodo
     * @return deveria devolver un String con el nombre de una nodo
     * */
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() == 0) {
            // Si la etiqueta no existe en absoluto
            return null;
        }

        Node node = nodeList.item(0);
        if (node != null && node.getFirstChild() != null) {
            return node.getFirstChild().getNodeValue();
        }
        return null;
    }
}
