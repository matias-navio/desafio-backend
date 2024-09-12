package com.matias.desafio_backend.desafio_backend.xml;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ImportXmlFile implements CommandLineRunner {

    private final ReadXmlFile readXmlFile;

    public ImportXmlFile(ReadXmlFile readXmlFile) {
        this.readXmlFile = readXmlFile;
    }

    @Override
    public void run(String... args) throws Exception {

        String filePah = "C:\\Users\\pc\\OneDrive\\Escritorio\\desafio-backend\\DesafioBackend.xml";
        readXmlFile.leerYGuardarEmpresas(filePah);
        System.out.println("Empresas importadas desde el XML y guardadas en la base de datos.");

    }
}
