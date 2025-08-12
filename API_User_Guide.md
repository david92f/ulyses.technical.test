# Guía de Usuario de la API de Ventas de Vehículos

Este documento describe los puntos de conexión (endpoints) disponibles en la API de gestión de ventas de vehículos, así como los detalles de cada operación.

***

### Información General

* **URL Base**: La URL base para todas las solicitudes es `http://localhost:8080/api`.
* **Autenticación**: Las solicitudes `POST`, `PUT` y `DELETE` requieren autenticación básica.
    * **Usuario**: `user`
    * **Contraseña**: `password`

***

### Controlador de Marcas (`BrandController`)

Punto de conexión principal: `/api/brands`

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `GET` | `/` | Obtiene una lista de todas las marcas. |
| `GET` | `/{id}` | Obtiene una marca específica por su ID. |
| `POST` | `/` | Crea una nueva marca. Requiere autenticación y un cuerpo de solicitud con el nombre de la marca. |
| `PUT` | `/{id}` | Actualiza una marca existente por su ID. Requiere autenticación y un cuerpo de solicitud con los datos actualizados. |
| `DELETE` | `/{id}` | Elimina una marca por su ID. Requiere autenticación. |

***

### Controlador de Vehículos (`VehicleController`)

Punto de conexión principal: `/api/vehicles`

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `GET` | `/` | Obtiene una lista de todos los vehículos. |
| `GET` | `/{id}` | Obtiene un vehículo específico por su ID. |
| `POST` | `/` | Crea un nuevo vehículo. Requiere autenticación y un cuerpo de solicitud con los datos del vehículo y el ID de la marca. |
| `PUT` | `/{id}` | Actualiza un vehículo existente por su ID. Requiere autenticación y un cuerpo de solicitud con los datos actualizados. |
| `DELETE` | `/{id}` | Elimina un vehículo por su ID. Requiere autenticación. |

***

### Controlador de Ventas (`SalesController`)

Punto de conexión principal: `/api/sales`

| Método | Ruta | Descripción |
| :--- | :--- | :--- |
| `GET` | `/` | Obtiene todas las ventas paginadas (10 elementos por página). Acepta un parámetro de consulta `page`. |
| `GET` | `/{id}` | Obtiene una venta específica por su ID. |
| `GET` | `/brands/{brandId}` | Obtiene una lista de ventas para una marca específica por su ID. |
| `GET` | `/vehicles/{vehicleId}` | Obtiene una lista de ventas para un vehículo específico por su ID. |
| `GET` | `/vehicles/bestSelling` | Obtiene los 5 vehículos más vendidos. Acepta parámetros de consulta opcionales `startDate` y `endDate` para filtrar por rango de fechas. |
