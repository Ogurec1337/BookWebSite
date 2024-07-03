# Book Management Application

A simple book management application with user authentication and book cataloging features.

## Features

- User registration and authentication
- Book catalog management
- Search functionality
- Role-based access control (Admin, Supplier, User)

## Requirements

- Java 17+
- Maven 3.5+
- MySQL 9.6+ (12+ recomended)

## Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/Ogurec1337/BookWebSite.git
    cd yourproject
    ```

2. **Set up the database:**

    - Create the database:

        ```sql
        CREATE DATABASE BookSite;
        ```

    - Create the necessary tables:

        ```sql
        CREATE TABLE book
        (
            id int serial PRIMARY KEY,
            name VARCHAR(64) NOT NULL,
            description VARCHAR(700),
            price int,
            authorVARCHAR(64) NOT NULL,
            year int,
            url VARCHAR(30) NOT NULL,
            CHECK (price > 0)
        )
        CREATE TABLE IF NOT EXISTS public.cart
        (
             id int serial PRIMARY KEY,
            user_id int,
            CONSTRAINT cart_user_id_fkey FOREIGN KEY (user_id)
                REFERENCES public.myuser (id) MATCH SIMPLE
        )

        CREATE TABLE IF NOT EXISTS public.myuser
        (
            id int serial PRIMARY KEY,
            username  VARCHAR(64) NOT NULL,
            email  VARCHAR(500),
            password  VARCHAR(30) ,
            role  VARCHAR(30),
        
        )

        CREATE TABLE book_cart
        (
            cart_id integer,
            book_id integer
        )
        ```

3. **Configure the application:**

    Copy `application.properties.example` to `application.properties` and update the database settings:

4. **Build and run the project:**

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

## Usage

After starting the application, you can access it at `http://localhost:8080`. Register a new user, log in, and start managing books.
