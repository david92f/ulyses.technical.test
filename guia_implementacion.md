# Guía de Implementación del Proyecto de Ventas de Vehículos

Este documento es una guía práctica para construir la aplicación de ventas de vehículos. Sigue estos pasos para implementar las funcionalidades requeridas.

### 1. Preparación y Estructura del Proyecto

Antes de empezar a programar, asegúrate de tener una base sólida.

1.  **Prerrequisitos**: Necesitas tener instalado **Java 21** y un entorno de desarrollo integrado (IDE) como IntelliJ IDEA, Visual Studio Code o Eclipse. El proyecto utiliza Spring Boot y Spring Data JPA, así que asegúrate de que tu IDE tiene las herramientas necesarias.

2.  **Configuración inicial**: Tu proyecto debe seguir la arquitectura de tres capas: `Controller`, `Service` y `Repository`. Tus entidades `Brand`, `Vehicle` y `Sale` deben estar definidas, y el archivo `application.properties` debe configurar la base de datos en memoria H2.

### 2. Configurar Spring Security

El primer paso es asegurar tu API.

1.  **Crea la clase de configuración**: Crea una clase llamada `SecurityConfig` y anótala con `@Configuration` y `@EnableWebSecurity`.

2.  **Define las reglas de acceso**: Dentro de esta clase, configura `HttpSecurity` para que los endpoints `GET` sean públicos (`permitAll()`) y los demás verbos (`POST`, `PUT`, `DELETE`) requieran autenticación (`authenticated()`).

3.  **Configura un usuario en memoria**: Para una autenticación simple, puedes definir un `UserDetailsService` en memoria que cargue las credenciales desde `application.properties`.

### 3. Implementar `SalesController` y el `SalesService`

Aquí es donde añades la lógica de negocio para las ventas.

#### **Nuevos Endpoints en `SalesController`**

* **Ventas por marca**:
    * Añade un método con la anotación `@GetMapping("/brands/{brandId}")`.
    * Este método debe llamar a un nuevo método en `SalesService`, por ejemplo, `getSalesByBrand(brandId)`.
* **Ventas por vehículo**:
    * Añade un método con la anotación `@GetMapping("/vehicles/{vehicleId}")`.
    * Este método debe llamar a `salesService.getSalesByVehicle(vehicleId)`.

#### **Paginación en `GET /api/sales`**

* Modifica el método `getAllSales` para que acepte un parámetro `@RequestParam(defaultValue = "0") int page`.
* En `SalesService`, implementa la lógica para devolver solo los 10 elementos que corresponden a la página solicitada.

#### **Endpoint de `bestSelling`**

* Crea un método con `@GetMapping("/vehicles/bestSelling")` que acepte los parámetros `@RequestParam` opcionales `startDate` y `endDate`.
* En el `SalesService`, implementa el siguiente flujo de lógica personalizada:
    * Recupera todas las ventas de la base de datos.
    * Usa un `Map` para contar las ventas por cada vehículo (`Map<Long, Integer>`).
    * Filtra los resultados del `Map` para encontrar los 5 vehículos con más ventas sin usar `Collections.sort()`. Un enfoque eficiente es iterar sobre el mapa y mantener una lista de los 5 principales en todo momento.

### 4. Crear el Filtro de Logs

Este filtro capturará información de cada solicitud y respuesta.

1.  **Crea una clase para el filtro**: Crea una clase que extienda `OncePerRequestFilter` o implemente `Filter` y anótala con `@Component`.
2.  **Define el método `doFilterInternal`**: Dentro de este método, captura la hora de inicio de la solicitud, llama a la cadena de filtros (`chain.doFilter`) y luego captura la hora de finalización.
3.  **Recupera y registra los datos**: Usa los objetos `HttpServletRequest` y `HttpServletResponse` para obtener el método, la URL, el código de estado y el tiempo de procesamiento. Escribe esta información en un archivo de log, por ejemplo, `app.log`.

### 5. Implementar el Sistema de Caché

El caché mejorará el rendimiento de las consultas para la entidad `Brand`.

1.  **Crea una clase para la caché**: Crea una clase `BrandCache` que tenga un `ConcurrentHashMap` para almacenar las marcas y un método para validar la caducidad.

2.  **Implementa el caché en `BrandService`**:
    * Modifica el método `getBrandById`.
    * Primero, verifica si la marca está en la caché y si no ha caducado.
    * Si está válida, devuélvela.
    * Si no, obtén la marca del repositorio, guárdala en la caché con una marca de tiempo de caducidad y luego devuélvela.
