# BiteCurve

BiteCurve is a Java web application for a food delivery workflow. It lets users register, log in, browse restaurants and menu items, manage a cart, place orders, view order history, submit reviews, update their profile, and use basic admin functionality.

## Features

- User registration and login
- Restaurant and menu browsing
- Add, update, and remove cart items
- Checkout and order placement
- Order history and order item details
- User profile management with profile image support
- Restaurant reviews
- Admin page for managing app data

## Tech Stack

- Java 17
- Jakarta Servlet API 6
- JSP and JSTL
- Maven
- MySQL
- Apache Tomcat 10+
- Gson

## How to Run Locally

1. Install Java 17, Maven, MySQL, and Apache Tomcat 10 or newer.
2. Create a MySQL database:

   ```sql
   CREATE DATABASE food_delivery_app;
   ```

3. Import the required database tables and seed data for the application. The repository includes `src/main/resources/database/reviews-table.sql` for the reviews table. Make sure the rest of your local BiteCurve schema includes tables used by the app, including users, restaurants, menu items, carts, orders, order items, payments, and tracking.
4. Configure the database connection with environment variables:

   ```bash
   DB_URL=jdbc:mysql://localhost:3306/food_delivery_app
   DB_USER=your_mysql_user
   DB_PASSWORD=your_mysql_password
   ```

   You can also pass Java system properties instead:

   ```bash
   -Ddb.url=jdbc:mysql://localhost:3306/food_delivery_app -Ddb.user=your_mysql_user -Ddb.password=your_mysql_password
   ```

5. Build the WAR file:

   ```bash
   mvn clean package
   ```

6. Deploy `target/food-delivery-app.war` to Tomcat 10+.
7. Open the application at:

   ```text
   http://localhost:8080/food-delivery-app/
   ```

## Database Setup

The application expects a MySQL database named `food_delivery_app` by default. The connection can be changed with `DB_URL`, `DB_USER`, and `DB_PASSWORD`.

Do not commit database passwords or local `.env` files. For deployment, configure these values in the hosting platform's environment variable or server configuration settings.

## Deployment Requirements

- Java 17 runtime
- Apache Tomcat 10+ or another Jakarta Servlet 6 compatible container
- MySQL database reachable from the application server
- Environment variables or JVM system properties for database credentials
- A built WAR file from `mvn clean package`

This project is a JSP/Servlet/Tomcat WAR application. It is not a Spring Boot application.
