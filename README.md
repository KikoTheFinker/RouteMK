# RouteMK  
[![RouteMK Trac Docs](https://img.shields.io/badge/TRAC-RouteMK%20Docs-6DB33F?style=for-the-badge&logo=mapbox&logoColor=white)](https://develop.finki.ukim.mk/projects/routemk)

![banner](https://github.com/user-attachments/assets/b1ebc5ff-6488-4af7-9a19-fc66ddef579a)

**Welcome to RouteMK!**

RouteMK provides a centralized transport platform in Macedonia, allowing online ticket purchases for transportation all in one place.

# 🛠️ General Run Instructions  
## Run with Docker

1. **Clone the repository**
   git clone <repo-url>
   cd /path/to/routemk

2. **Run the full application**
   docker-compose up --build

3. **Run only the database** (daemonized; omit `-d` if you want logs in the foreground):
   docker-compose up -d db

4. **Run only the application** (daemonized):
   docker-compose up -d app

   Requires a local PostgreSQL server running on port 5432 with the schema already created, where you run the DDL queries manually.
- Run the DDL queries found in `scripts/`:
     - First: RouteMK.sql
     - Then: the 2 views
   The order matters of these scripts, do not skip them.
   
## Run Without Docker

If you don’t want to use Docker, you can run RouteMK locally:

1. **Install dependencies**
   - Java 17+
   - Maven
   - PostgreSQL (running locally on port 5432)

2. **Prepare the database**
- Create a new database (e.g., by name `RouteMK`)
- Run the DDL queries found in `scripts/`:
     - First: `RouteMK.sql`
     - Then: the other 2 views in this directory
   You can either do this in the terminal or with [pgadmin](https://www.pgadmin.org/) as an easier tool.

3. **Configure application properties**
   In `src/main/resources/application.properties` (you have to create it), update database credentials if needed, here is an example file if you followed the instructions above:

```bash
spring.datasource.url=jdbc:postgresql://localhost:5432/RouteMK
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
Replace `<username>` and `<password>` with your credentials to PostgreSQL.

4. **Build and run the application - Or optionally use IntelliJ to handle everything for you**
```bash
   mvn clean install
   mvn spring-boot:run
```
The application will then be available at:
`http://localhost:8080`
