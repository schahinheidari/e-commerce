# E-Commerce Backend System

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

A full-featured e-commerce backend system with JWT authentication, product management, and shopping cart functionality.

## Features

### Core Modules
- **User Authentication**
  - JWT-based authentication/authorization
  - Role-based access control (Admin/User)
  - Secure password storage with BCrypt

- **Product Management**
  - CRUD operations for products
  - Category-based organization
  - Inventory tracking

- **Shopping Cart**
  - Add/remove items
  - Quantity management
  - Session persistence

- **Order Processing**
  - Order status tracking (Pending → Processing → Shipped → Delivered)
  - Order history

### Technical Highlights
- Spring Security with JWT
- RESTful API design
- Proper exception handling
- Data initialization with default admin/user accounts
- ModelMapper for DTO conversions
- Hibernate ORM with JPA

## API Endpoints

| Endpoint                | Method | Description                     | Auth Required |
|-------------------------|--------|---------------------------------|---------------|
| `/api/auth/login`       | POST   | User login                      | No            |
| `/api/products`         | GET    | List all products               | No            |
| `/api/products/{id}`    | GET    | Get product details             | No            |
| `/api/products`         | POST   | Create new product              | Admin         |
| `/api/cart/items`       | POST   | Add item to cart                | User          |
| `/api/cart/items/{id}`  | DELETE | Remove item from cart           | User          |

## Database Schema

```mermaid
erDiagram
    USER ||--o{ CART_ITEM : has
    USER {
        Long id PK
        String email
        String password
    }
    
    ROLE ||--o{ USER : has
    ROLE {
        Long id PK
        String name
    }
    
    PRODUCT ||--o{ CART_ITEM : in
    PRODUCT {
        Long id PK
        String name
        BigDecimal price
    }
    
    CATEGORY ||--o{ PRODUCT : belongs_to
    CATEGORY {
        Long id PK
        String name
    }
    
    CART_ITEM {
        Long id PK
        int quantity
    }
```

## Installation

1. **Prerequisites**
    - Java 17+
    - MySQL 8.0+
    - Maven


2. **Configuration**
   Create `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   
   auth.jwt.secret=your-jwt-secret-key
   auth.jwt.expiration=86400000 # 24h
   ```

3. **Run**
   ```bash
   mvn spring-boot:run
   ```

## Initial Data

The system automatically creates on startup:
- Default roles: `ROLE_ADMIN`, `ROLE_USER`
- Admin user: `admin@example.com` / `123456`
- Test user: `user@example.com` / `123456`
- Sample categories and products

## Security

- JWT authentication
- Password encryption with BCrypt
- Role-based authorization
- CSRF protection
- Input validation