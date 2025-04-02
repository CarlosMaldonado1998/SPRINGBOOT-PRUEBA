# PRUEBA-TECNICA

**PRUEBA-TECNICA** es una aplicación implementada usando el framework Spring Boot y utilizando una base de datos local de MySQL. La aplicación expone una API RESTful que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) para gestionar clientes, cuentas bancarias y movimientos.

## Tecnologías

- **Spring Boot**: Framework para el desarrollo de aplicaciones Java basadas en microservicios.
- **MySQL**: Base de datos relacional para almacenar la información.
- **JPA (Java Persistence API)**: Para interactuar con la base de datos utilizando entidades.
- **Spring Data JPA**: Simplifica el acceso a datos.
- **JUnit**: Para la implementación de pruebas unitarias.
  
## Funcionalidades

- **Clientes**: CRUD para gestionar clientes.
- **Cuentas**: CRUD para gestionar cuentas bancarias, con operaciones de saldo y tipo de cuenta.
- **Movimientos**: CRUD para gestionar los movimientos de las cuentas, con validaciones de saldo suficiente y tipo de transacción.


## Requisitos

1. Java 11 o superior.
2. MySQL configurado y corriendo en el puerto 3306.
3. Configuración de la base de datos en el archivo `application.properties`.

## Instalación

1. Clona este repositorio en tu máquina local:
   ```bash
   git clone https://github.com/CarlosMaldonado1998/SPRINGBOOT-PRUEBA.git


## Archivos adicionales
1. Colección de Postman:
[postman\collection.postman_collection.json]
Contiene la colección de endpoints para probar la API.

3. Script SQL: script.sql

Contiene los scripts necesarios para crear la base de datos y las tablas para la aplicación.

[postman\scripts.sql]
