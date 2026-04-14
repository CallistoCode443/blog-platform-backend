# 📝 Blogplatform

A RESTful blog platform backend built with Java and Spring Boot.

## 🚀 Getting Started

### Prerequisites

- Docker & Docker Compose
- Java 21
- Maven

### Run with Docker Compose

```bash
git clone https://github.com/CallistoCode443/blog-platform-backend
cd blog-platform-backend
docker-compose up --build
```

The API will be available at `http://localhost:8080`.

## 🛠 Tech Stack

- **Java 21** + **Spring Boot 4**
- **Spring Security** + **JWT** (access + refresh tokens)
- **Spring Data JPA** + **Hibernate**
- **PostgreSQL**
- **Flyway** — database migrations
- **MapStruct** — entity/DTO mapping
- **Docker** + **Docker Compose**

## 📡 API Endpoints

### 🔐 Auth

| Method | Endpoint               | Description          | Auth required |
|--------|------------------------|----------------------|---------------|
| POST   | `/api/v1/auth/signup`  | Register a new user  | ❌             |
| POST   | `/api/v1/auth/signin`  | Login                | ❌             |
| POST   | `/api/v1/auth/logout`  | Logout               | ❌             |
| POST   | `/api/v1/auth/refresh` | Refresh access token | ❌             |

**Signup request:**

```json
{
  "email": "user@example.com",
  "username": "username",
  "password": "password123"
}
```

**Signin request:**

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

---

### 📂 Categories

| Method | Endpoint                  | Description        | Auth required |
|--------|---------------------------|--------------------|---------------|
| GET    | `/api/v1/categories`      | Get all categories | ❌             |
| POST   | `/api/v1/categories`      | Create category    | ✅             |
| DELETE | `/api/v1/categories/{id}` | Delete category    | ✅             |

**Create category request:**

```json
{
  "name": "Backend"
}
```

---

### 🏷️ Tags

| Method | Endpoint            | Description  | Auth required |
|--------|---------------------|--------------|---------------|
| GET    | `/api/v1/tags`      | Get all tags | ❌             |
| POST   | `/api/v1/tags`      | Create tag   | ✅             |
| DELETE | `/api/v1/tags/{id}` | Delete tag   | ✅             |

**Create tag request:**

```json
{
  "name": "spring-boot"
}
```

---

### 📄 Posts

| Method | Endpoint                       | Description                         | Auth required |
|--------|--------------------------------|-------------------------------------|---------------|
| GET    | `/api/v1/posts`                | Get all published posts (paginated) | ❌             |
| GET    | `/api/v1/posts/{id}`           | Get post by ID                      | ❌             |
| POST   | `/api/v1/posts`                | Create post                         | ✅             |
| PATCH  | `/api/v1/posts/{id}`           | Update post                         | ✅             |
| DELETE | `/api/v1/posts/{id}`           | Delete post                         | ✅             |
| POST   | `/api/v1/posts/{id}/publish`   | Publish post                        | ✅             |
| POST   | `/api/v1/posts/{id}/unpublish` | Unpublish post                      | ✅             |

**Create post request:**

```json
{
  "title": "My first post",
  "content": "Post content here...",
  "categoryId": "uuid",
  "tags": [
    "spring",
    "java"
  ]
}
```

**Query parameters for `GET /api/v1/posts`:**

| Param        | Type   | Description              |
|--------------|--------|--------------------------|
| `categoryId` | UUID   | Filter by category       |
| `tagId`      | UUID   | Filter by tag            |
| `authorId`   | UUID   | Filter by author         |
| `q`          | String | Search by title          |
| `page`       | int    | Page number (default: 0) |
| `size`       | int    | Page size (default: 20)  |

---

### 👤 Users

| Method | Endpoint           | Description                 | Auth required |
|--------|--------------------|-----------------------------|---------------|
| GET    | `/api/v1/users/me` | Get current user profile    | ✅             |
| PATCH  | `/api/v1/users/me` | Update current user profile | ✅             |

**Update profile request:**

```json
{
  "username": "newUsername",
  "email": "new@example.com",
  "password": "newPassword",
  "currentPassword": "currentPassword"
}
```

## 🔑 Authentication

Authentication is based on JWT tokens stored in **HttpOnly cookies**. After signing in, two cookies are set
automatically:

- `access_token` — short-lived (15 minutes)
- `refresh_token` — long-lived (7 days)

When the access token expires, call `POST /api/v1/auth/refresh` to get new tokens. The refresh token is passed
automatically via cookie.

## 🗺️ Roadmap

- [ ] Comments on posts
- [ ] Post likes
- [ ] Email notifications
- [ ] Admin role with extended permissions
- [ ] Integration tests with Testcontainers