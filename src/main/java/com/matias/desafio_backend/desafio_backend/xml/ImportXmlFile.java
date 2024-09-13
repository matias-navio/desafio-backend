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

        // ruta donde esta el archivo XML
        String filePah = "C:\\Users\\pc\\OneDrive\\Escritorio\\desafio-backend\\DesafioBackend.xml";

        // metodo que lee las empresas obtenidas y las garda en la DB
        readXmlFile.readAndSaveCompany(filePah);

    }
}
