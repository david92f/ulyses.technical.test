# Guía de Usuario para Ejecutar la Aplicación

Esta guía te ayudará a poner en marcha el proyecto de ventas de vehículos si no tienes experiencia en desarrollo.

### 1. Requisitos previos

* **Java 21**: Asegúrate de tenerlo instalado en tu computadora. Puedes verificarlo abriendo la terminal (o Símbolo de sistema) y escribiendo `java --version`. Si no lo tienes, puedes descargarlo e instalarlo.
* **IDE (Entorno de Desarrollo Integrado)**: Un programa como **IntelliJ IDEA Community Edition**, **Visual Studio Code**, **Eclipse**, **Netbeans** es ideal.

### 2. Abrir y ejecutar el proyecto

1.  **Descarga y descomprime**: Descarga el proyecto en formato ZIP y descomprímelo en una carpeta.
2.  **Abre el IDE**: Inicia tu IDE y selecciona "Open" para abrir la carpeta del proyecto que acabas de descomprimir.
3.  **Ejecuta la aplicación**: Dentro de tu IDE, busca el archivo principal de la aplicación. Suelen tener un nombre como `Application.java` y una etiqueta `main`. Haz clic derecho sobre este archivo y selecciona "Run" (ejecutar).
4.  **Verifica la consola**: En la parte inferior del IDE, se abrirá una consola que te mostrará los mensajes del programa. Busca la línea que indica que el servidor ha iniciado, probablemente algo como `Started Application in ... seconds (JVM running for ...)`.

### 3. Usar la API (Probar la aplicación)

Una vez que la aplicación está en marcha, puedes usar tu navegador o una herramienta como **Postman** para probar las funciones. El proyecto se ejecuta en la dirección `http://localhost:8080`.

#### **Endpoints públicos (lectura de datos)**

Puedes ver la información sin necesidad de usuario o contraseña. Simplemente pega estas URL en tu navegador:

* **Ver todas las marcas**: `http://localhost:8080/api/brands`
* **Ver un vehículo específico**: `http://localhost:8080/api/vehicles/1` (cambia el `1` por el ID que quieras)
* **Ver ventas por página**: `http://localhost:8080/api/sales?page=0` (cambia el `0` por el número de página)

#### **Endpoints protegidos (crear, actualizar, eliminar)**

Para estas acciones, necesitarás autenticarte.

* **Herramienta Postman**: Usa Postman y selecciona la opción de "Basic Auth".
* **Credenciales**: El usuario y la contraseña se encuentran en el archivo `src/main/resources/application.properties` del proyecto.

---

### Ejemplos para Probar los Endpoints Protegidos en Postman

#### **1. Crear un Vehículo (`POST`)**
* **Método**: `POST`
* **URL**: `http://localhost:8080/api/vehicles`
* **Autorización**: En la pestaña "Authorization", selecciona **"Basic Auth"** y usa las credenciales del archivo `application.properties` (por defecto, `user: user` y `pass: password`).
* **Body**: En la pestaña "Body", selecciona **"raw"** y el tipo **"JSON"**. Introduce el siguiente JSON para crear un nuevo vehículo. Asegúrate de que el `id` de la marca (`brand`) exista en tu base de datos.

```json
{
    "name": "Focus",
    "model": "Ford",
    "year": 2023,
    "brand": {
        "id": 1,
        "name": "Ford"
    }
}
```

* **Resultado esperado**: La respuesta será un código de estado `201 Created` y el objeto del vehículo creado con un nuevo ID.

#### **2. Actualizar un Vehículo (`PUT`)**
* **Método**: `PUT`
* **URL**: `http://localhost:8080/api/vehicles/2` (cambia el `2` por el ID del vehículo que quieras actualizar).
* **Autorización**: En la pestaña "Authorization", selecciona **"Basic Auth"** con las mismas credenciales.
* **Body**: En la pestaña "Body", selecciona **"raw"** y el tipo **"JSON"**. Proporciona el objeto completo con los datos actualizados.

```json
{
    "id": 2,
    "name": "Civic",
    "model": "Honda",
    "year": 2024,
    "brand": {
        "id": 2,
        "name": "Honda"
    }
}
```

* **Resultado esperado**: La respuesta será un código de estado `200 OK` y el objeto del vehículo actualizado.

#### **3. Eliminar un Vehículo (`DELETE`)**
* **Método**: `DELETE`
* **URL**: `http://localhost:8080/api/vehicles/3` (cambia el `3` por el ID del vehículo que quieras eliminar).
* **Autorización**: En la pestaña "Authorization", selecciona **"Basic Auth"** con las mismas credenciales.
* **Body**: No necesitas un cuerpo para esta solicitud.
* **Resultado esperado**: La respuesta será un código de estado `204 No Content`.
