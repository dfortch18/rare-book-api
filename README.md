# Rare Book API

[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/es/)
[![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=flat-square&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat-square&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=flat-square&logo=spring&logoColor=white)](https://spring.io/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=flat-square&logo=spring-boot)](https://spring.io/projects/spring-boot)
[![JUnit](https://img.shields.io/badge/JUnit-25A162?style=flat-square&logo=junit5&logoColor=white)](https://junit.org/junit5/)
[![Postman](https://img.shields.io/badge/Postman-FF6C37?style=flat-square&logo=postman&logoColor=white)](https://www.postman.com/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=docker&logoColor=white)](https://www.docker.com/)

REST API to manage a collection of rare books.

## Features

- Manage Rare Books (Get, Create, Update, Delete)
- Manage Book Categories
- Search and filter books by various criteria
- Pagination support
- Validation and error handling

## Getting Started

These instructions will help you set up and run the project on your local machine for development and testing purposes.

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- MySQL 8.x
- Docker (optional, for containerized setup)

### Configuration

#### Using environment variables

Set the required environment variables for the application based on [`.env.example`](./.env.example):

```bash
SPRING_SERVER_PORT=
SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_FLYWAY_LOCATIONS=classpath:db/migration/mysql
SPRING_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
```

Now set the [`application.properties`](./src/main/resources/application.properties) file:

```properties
spring.server.port=${SPRING_SERVER_PORT}

spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
spring.datasource.driver-class-name=${SPRING_DATASOURCE_DRIVER_CLASS_NAME}
    
spring.jpa.properties.hibernate.dialect=${SPRING_HIBERNATE_DIALECT}
  
spring.flyway.locations=${SPRING_FLYWAY_LOCATIONS}
```

#### Using fixed values

Alternatively you can set fixed values in the [`application.properties`](./src/main/resources/application.properties) file:

```properties
spring.server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/rarebookdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
  
spring.flyway.locations=classpath:db/migration/mysql
```

### Use

1. **Build the project:**

    ```bash
    mvn clean install
    ```

2. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

The API will be available at `http://localhost:<server port>/api/v1/`.

## Running with Docker

This project includes support for running the Rare Book API in a containerized environment using Docker and Docker Compose.

### Setup

1. **Build the Docker Image**

   ```bash
   docker build -t rare-book-api:latest .
   ```

2. **Run with Docker Compose:**
   ```bash
   docker compose up
   ```

### Services

- **app:** Rare Book API on port `8080`.
- **mysql:** MySQL server on port `3307`.
- **phpmyadmin:** phpMyAdmin on `http://localhost:9090`.

### Shutting Down

```bash
docker compose down
```

## API Endpoints

### Rare Book

| **Method** | **Endpoint**                  | **Description**                                     |
|------------|-------------------------------|-----------------------------------------------------|
| `GET`      | `/api/v1/books`               | Get a paginated list of books with optional filters |
| `GET`      | `/api/v1/books/{id}`          | Get a book by its ID                                |
| `POST`     | `/api/v1/books`               | Create a new book                                   |
| `PUT`      | `/api/v1/books/{id}`          | Update an existing book by its ID                   |
| `DELETE`   | `/api/v1/books/{id}`          | Delete a book by its ID                             |

### Category

| **Method** | **Endpoint**              | **Description**             |
|------------|---------------------------|-----------------------------|
| `GET`      | `/api/v1/categories`      | List all categories         |
| `GET`      | `/api/v1/categories/{id}` | Get a category by ID        |
| `POST`     | `/api/v1/categories`      | Create a new category       |
| `PUT`      | `/api/v1/categories/{id}` | Update an existing category |
| `DELETE`   | `/api/v1/categories/{id}` | Delete a category           |

## Postman Collection

A Postman collection is provided for testing the API.

### How to use the Postman Collection

1. **Download the Collection:** Get the [Postman Collection File](./resources/rare-book-api-collection.json)
2. **Import into Postman:**

   - Open Postman.
   - Click `Import` and select the `rare-book-api-collection.json` file.
3. **Run Requests:** You can now run the requests to interact with the API endpoints.

## Testing

This project uses JUnit for unit and integration tests. You can run the tests with the following command:

```bash
mvn test
```

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.


## Acknowledgements

- [**wait_for_it.sh:**](https://github.com/vishnubob/wait-for-it) Utility script for docker containers.