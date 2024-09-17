## Desafio de backend 

Este proyecto consistió en la lectura de un archivo XML que contenía una lista de empresas, con el objetivo de transformar esos datos en un archivo Excel. Utilicé la librería Apache POI, que permite la creación y manipulación de documentos de Microsoft Excel de manera programática en Java.

- Realicé validaciones de los tags del archivo XML, avisando con mensajes si se encontraban errores.
- Creación de dos hojas distintas, una para Empresas y otra para Movimientos de las empresas.
- En la hoja Movimientos cree un hipervínculo que te lleva directo a la Empresa con ese NroContrato.
- Se respetó el tipo de dato de cada columna en las hojas del Excel.
- El archivo Excel es creado en tiempo de ejecución y no con anterioridad.
- También tiene un pequeño servicio de API REST para listar las empresas y los movimientos mediante endpoints.

Agregué de manera no obligatoria una base de datos donde se guardan los datos de Empresas y Movimientos, para poder realizar el servicio REST me nanera más comoda y para tener un mayor control sobre lo que es el manejo de las empresas ya sea agregar, modificar, eliminar, etc.

Las tablas de la base de datos se crean automaticamnete gracias al ORM de Hibernate.
 - Se necesita tener la DB creada con anterioridad con el nombre de db_desafio.
 - Se crea uan tabla movements y una tabla companies.

Se puede consumir desde postman 3 endpoints creados que son:
 - http://127.0.0.1:8080/companies/all - Enspoint de tipo GET que devuelve la lista de empresas.
 - http://127.0.0.1:8080/companies/{nroContrato} - Endpoint de tipo GET que devuelve una empresa empecifica.
 - http://127.0.0.1:8080/movements/all - Endpoint de tipo GET que devuelve la lista de movimientos.

  
