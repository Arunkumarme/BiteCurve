# BiteCurve Deployment

BiteCurve is packaged as a standard Java WAR file for Tomcat 10+.

## Required Runtime Settings

Configure these environment variables on the deployment platform:

```text
DB_URL=jdbc:mysql://<host>:<port>/<database_name>
DB_USER=<database_user>
DB_PASSWORD=<database_password>
```

The deployed server must be able to connect to the MySQL database.

## Database Initialization

Initialize MySQL from the project database export before starting the application:

```bash
mysql -u <database_user> -p <database_name> < database/bitecurve_database.sql
```

The export should contain the BiteCurve schema and seed data for the application tables, including `users`, `restaurants`, `menu_items`, `cart`, `orders`, `order_items`, `payments`, and `reviews`.

## Deploy as a WAR

1. Build the project:

   ```bash
   mvn clean package
   ```

2. Upload `target/food-delivery-app.war` to a Tomcat 10+ server.
3. Set `DB_URL`, `DB_USER`, and `DB_PASSWORD` in the server environment.
4. Restart Tomcat.

## Deploy with Docker

This repository includes a `Dockerfile` that builds the WAR with Maven and runs it on Tomcat 10.

Build locally:

```bash
docker build -t bitecurve .
```

Run locally:

```bash
docker run --rm -p 8080:8080 ^
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/food_delivery_app ^
  -e DB_USER=your_mysql_user ^
  -e DB_PASSWORD=your_mysql_password ^
  bitecurve
```

Open:

```text
http://localhost:8080/
```

## Suitable Hosting Options

Use a platform that supports Java WAR/Tomcat applications or Docker containers, such as:

- A VPS running Tomcat 10+
- Render or Railway using Docker
- Azure App Service with a Tomcat runtime
- AWS Elastic Beanstalk with Tomcat

Do not deploy database credentials in source control. Use the platform's environment variable or secret manager feature.
