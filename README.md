![image](https://github.com/user-attachments/assets/0e21bf29-617c-4d4d-8582-8364797e436c)## Desafio de backend 

Este proyecto consistió en la creación de un pequeño servico REST que nos permite enviarle un archivo XML con una lista de empresas y nos devuelve un archivo Excel generado a partir del XML con la información de las empresas y sus movimientos.

- Realicé validaciones de los tags del archivo XML, avisando con mensajes si se encontraban errores.
- Creación de dos hojas distintas, una para Empresas y otra para Movimientos de las empresas.
- En la hoja Movimientos cree un hipervínculo que te lleva directo a la Empresa con ese NroContrato.
- Se respetó el tipo de dato de cada columna en las hojas del Excel.
- El archivo Excel es creado en tiempo de ejecución y no con anterioridad.
- También tiene un pequeño servicio de API REST que recibe el XML y nos devuelve el Excel.

Se puede consumir desde postman 1 endpoints creados que es:
 . http://localhost:8080/api/convert-xml-to-excel

Este es un endpoint de tipo POST.
Como se debe configurar:
- Primero poner que sea de tipo POST.
- En el body poner form-data.
- En la key del valor porer 'file' y que sea de tipo file, no text.
- En value cargar el archivo DesafioBackend.xml.
- Ejecutarlo.

Al ejecutarlo debería crearse el libro de excel en la ruta de las DESCARGAS de tu computadora.

