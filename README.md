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
2. Create and import the MySQL database:

   ```sql
   CREATE DATABASE food_delivery_app;
   ```

   ```bash
   mysql -u your_mysql_user -p food_delivery_app < database/bitecurve_database.sql
   ```

   The included export contains the BiteCurve schema and sanitized seed data used by the application, including `users`, `restaurants`, `menu_items`, `cart`, `orders`, `order_items`, `payments`, and `reviews`.

3. Configure the database connection with environment variables:

   ```bash
   DB_URL=jdbc:mysql://localhost:3306/food_delivery_app
   DB_USER=your_mysql_user
   DB_PASSWORD=your_mysql_password
   ```

   You can also pass Java system properties instead:

   ```bash
   -Ddb.url=jdbc:mysql://localhost:3306/food_delivery_app -Ddb.user=your_mysql_user -Ddb.password=your_mysql_password
   ```

4. Build the WAR file:

   ```bash
   mvn clean package
   ```

5. Deploy `target/food-delivery-app.war` to Tomcat 10+.
6. Open the application at:

   ```text
   http://localhost:8080/food-delivery-app/
   ```

## Database Setup

The application expects a MySQL database named `food_delivery_app` by default. The connection can be changed with `DB_URL`, `DB_USER`, and `DB_PASSWORD`.

Use `database/bitecurve_database.sql` to initialize a fresh local or production database:

```bash
mysql -u your_mysql_user -p food_delivery_app < database/bitecurve_database.sql
```

Do not commit database passwords or local `.env` files. For deployment, configure these values in the hosting platform's environment variable or server configuration settings.

## Deployment Requirements

- Java 17 runtime
- Apache Tomcat 10+ or another Jakarta Servlet 6 compatible container
- MySQL database reachable from the application server
- Environment variables or JVM system properties for database credentials
- A built WAR file from `mvn clean package`

This project is a JSP/Servlet/Tomcat WAR application. It is not a Spring Boot application.

## Docker Deployment

The included `Dockerfile` builds the WAR with Maven and deploys it to Tomcat 10.1:

```bash
docker build -t bitecurve .
docker run --rm -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/food_delivery_app \
  -e DB_USER=your_mysql_user \
  -e DB_PASSWORD=your_mysql_password \
  bitecurve
```

For production, set `DB_URL`, `DB_USER`, and `DB_PASSWORD` as platform secrets or environment variables. Never place real credential values in source control.
