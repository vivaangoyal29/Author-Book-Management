# Library Management Spring Boot App

This project implements a Spring Boot CRUD application with JSP views for two related entities:

- `Author`
- `Book`

It satisfies the assignment requirement for **Create, Read, and Update** operations, including a custom repository inner join query.

## Tech Stack

- Java 17
- Spring Boot 3
- Spring MVC
- Spring Data JPA
- H2 Database (in-memory)
- JSP + JSTL + EL
- JUnit 5 + Mockito

## Features Implemented

### 1) Populate Database

- Tables are auto-created from JPA entities (`ddl-auto=create`).
- `data.sql` inserts **10 rows in `authors`** and **10 rows in `books`**.

### 2) Create Operation

- Create Author: `/authors/new`
- Create Book: `/books`
- Integrity violation handling is implemented in controller methods (e.g., duplicate author email).

### 3) Read Operation

- Book listing page: `/books`
- Custom inner join query in repository:
    - `BookRepository#findAllBooksWithAuthorDetails()`
    - Returns `BookAuthorView` DTO with combined book + author data.

### 4) Update Operation

- Update Book: `/books/{id}/edit` -> POST `/books/{id}`
- Update Author: `/authors/{id}/edit` -> POST `/authors/{id}`

## Project Structure

- `src/main/java/com/bits/library/entity` - JPA entities
- `src/main/java/com/bits/library/repository` - repository interfaces and custom query
- `src/main/java/com/bits/library/service` - service layer business logic
- `src/main/java/com/bits/library/controller` - MVC controller
- `src/main/webapp/WEB-INF/jsp` - JSP pages
- `src/main/resources/static/css` - CSS styling
- `src/test/java/com/bits/library` - unit and repository tests

## How to Run

### Option A: With Maven Installed

```bash
mvn spring-boot:run
```

### Option B: Build then Run

```bash
mvn clean package
java -jar target/library-management-0.0.1-SNAPSHOT.jar
```

Open:

- [http://localhost:8080/books](http://localhost:8080/books)
- [http://localhost:8080/authors/new](http://localhost:8080/authors/new)

## Run Tests

```bash
mvn test
```

Tests included:

- Repository test for custom inner join query
- Service tests for `BookService` and `AuthorService` with Mockito

## GitHub URL

Add your repository URL here after pushing:

`https://github.com/<your-username>/<your-repo-name>`
