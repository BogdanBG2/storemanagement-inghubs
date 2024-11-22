# Store Management System

This project is a Store Management System that allows users to manage products and users within a store. It provides features to handle product inventory, user roles, and authentication.

## Features

- **Product Management**: Handle product inventory including adding, updating, and deleting products.
- **Authentication**: Secure user authentication using JWT tokens.
- **User Role Management**: Assign roles (ADMIN, USER) to users.

## Project Structure

- `src/main/java/com/bogdan/storemanagement_inghubs/config`: Configuration classes and properties.
- `src/main/java/com/bogdan/storemanagement_inghubs/controller`: Controller classes for API endpoints.
- `src/main/java/com/bogdan/storemanagement_inghubs/dto`: Data Transfer Objects utilized in requests and responses.
- `src/main/java/com/bogdan/storemanagement_inghubs/model`: Contains model classes for `User` and `Product`.
- `src/main/java/com/bogdan/storemanagement_inghubs/repository`: Repository interfaces for `User` and `Product`.
- `src/main/java/com/bogdan/storemanagement_inghubs/security`: Security configurations and user details service.
- `src/main/java/com/bogdan/storemanagement_inghubs/service`: Service classes for business logic.
- `src/main/java/com/bogdan/storemanagement_inghubs/utils`: Utility classes.
- `src/main/resources`: App configuration files, initial data, and SQL scripts.

## Setup and Build

1. **Prerequisites**: Ensure you have Java and Maven installed.
2. **Build**: Use the Maven wrapper to build the project:
   ```sh
   ./mvnw clean install
   ```
3. **Run**: After building, run the application:
   ```sh
   java -jar target/storemanagement.jar
   ```

## Configuration

- **Database**: Configure your database settings in `application.properties`, and ensure it's up and running.
- **Initial Data**: Modify the initial data in `src/main/resources/data.sql` if needed.
- **JWT Secret**: Define the JWT secret in `application.properties` for token generation.

## Endpoints

| Endpoint                   | Method | Description                                | Query Parameters                               |
|----------------------------| --- |--------------------------------------------|------------------------------------------------|
| `POST /login`              | `POST` | Authenticate user and generate JWT token.  | ---                                            |
| `POST /register`           | `POST` | Register a new user.                       | --- |                                           |
| `GET /users`               | `GET` | Retrieve a list of all users.              | Name substring, role                           |
| `GET /users/me`            | `GET` | Retrieve the currently authenticated user. | ---                                            |
| `GET /users/:id`           | `GET` | Retrieve a user by ID.                     | ---                                            |
| `POST /users/:id/role`     | `PUT` | Assign a role to a user.                   | New role of the given user                     |
| `DELETE /users/me`         | `DELETE` | Delete the currently authenticated user.   | ---                                            |
| `GET /products`            | `GET` | Retrieve a list of all products.           | Name substring, min price, max price, category |
| `POST /products`           | `POST` | Add a new product.                         | ---                                            |
| `GET /products/:id`        | `GET` | Retrieve a product by ID.                  | ---                                            |
| `POST /products/:id.price` | `PUT` | Update the price a product.                | New price of the given product                 |
| `DELETE /products/:id`     | `DELETE` | Delete a product.                          | ---                                            |