# Instructions

### What’s included in this repository?
This repository contains a Spring Boot application with the following structure:

- The domain includes three entities: **Brand**, **Vehicle**, and **Sale**.
   - A Brand can have multiple Vehicles.
   - A Sale is associated with a Vehicle, a Brand, and a date.
- The application exposes a REST API that allows basic **CRUD operations**.
- Each entity is handled through its own **Controller**, **Service**, and **Repository**.
- The application uses **Spring Data JPA** to interact with the database.
- It uses an **H2 in-memory database**, preloaded with sample data, and is configured to run on port `8080`.

---

### What do you need to implement?

1. **Implement Spring Security:**
   - All **GET** endpoints must remain public (no authentication required).
   - All **POST**, **PUT**, and **DELETE** endpoints must require authentication and authorization.

2. **Add the following endpoints to the `SalesController`:**
   - `GET /api/sales/brands/{brandId}` → Return all sales for a given brand.
   - `GET /api/sales/vehicles/{vehicleId}` → Return all sales for a given vehicle.
   - Modify the existing `GET /api/sales` endpoint and add pagination to it.
     - Each response must return **10 items per page**.
     - The **page number** must be accepted as an optional query parameter.
     - If the page number is not provided, the API should return the **first page by default**.

3. **Implement a new endpoint in `SalesController`:**
   - `GET /api/sales/vehicles/bestSelling` → Return the **top 5 best-selling vehicles**.
   - Allow optional filtering by **start date** and **end date**.
   - You must retrieve all sales from the database (as currently implemented).
      - **Do not use** SQL-level sorting, `Collections.sort()`, or any built-in sort utilities.
      - Implement your **own sorting logic** using Java code, considering performance (imagine millions of records).
      - You may create additional models or helper classes as needed.

4. **Create a custom filter or interceptor using Spring:**
   - It must **log all incoming API requests and outgoing responses** to a file.
   - The log must include:
      - Request date and time
      - HTTP method
      - Request URL
      - Response status code
      - Processing time (in milliseconds)

5. **Implement a caching system for the GET endpoints of Brand**
   - Check if the data exists in the cache and has not expired.
   - If it does not exist or has expired, call the supplier.
   - Update the cache with the new value.
   - Handle concurrency properly.

---

### Additional Information

- You must use **Java 21**.
- **Do not use any external libraries** — all code must be implemented by you.
- If there’s something you’re not sure how to do, don’t worry — just give it your best effort.
- We will carefully review your code and provide feedback as soon as possible.
 
