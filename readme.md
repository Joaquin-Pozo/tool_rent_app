# 🛠️ Tool Rent App
**Created by:** Joaquin Pozo  
**Universidad de Santiago de Chile (USACH)**

---

## 📌 Description
Tool Rent App is a web application developed with **Spring Boot** and **MySQL** for tool leasing management.

---

## ✅ Pre-requisites

Before running the project, make sure to install:

- **Spring Boot 3.5.5**
  ```bash
  spring --version
- **Java 17**
  ```bash
  java --version

- **MySQL 8.0.43**
  ```bash
  mysql --version

## ⚙️ Database Setup

1. Open MySQL terminal:
```bash
   mysql -u root -p
```
2. Create database and user:
```mysql
CREATE DATABASE tool_rent_db;
CREATE USER 'user'@'%' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON tool_rent_db.* TO 'user'@'%';
FLUSH PRIVILEGES;
```
3. Configure application.properties:
```properties
server.port=8090
spring.datasource.url=jdbc:mysql://${DB_HOST}:3306/tool_rent_db
spring.datasource.username=user
spring.datasource.password=password123
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
spring.application.name=tool-rent-app
```
## ▶️ Run the project
From IntelliJ IDEA or terminal: 
```bash
   ./mvnw spring-boot:run
```