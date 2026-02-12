# DDD-with-Spring-boot

This project is a demonstration of Domain-Driven Design principles with Spring Boot.

[![CI Pipeline](https://github.com/bharatpatel/DDD-with-Spring-boot/actions/workflows/ci.yml/badge.svg)](https://github.com/bharatpatel/DDD-with-Spring-boot/actions/workflows/ci.yml)

## Building the Project

To build the project locally, run the following command:

```bash
./mvnw verify
```

This command will compile the code, run tests, and package the application.

## Continuous Integration

This project uses GitHub Actions for its CI pipeline. The workflow, defined in `.github/workflows/ci.yml`, ensures code quality by performing the following checks on every push and pull request to the `main` branch:

- **Code Style**: Enforces consistent code style using the Spotless plugin.
- **Test Coverage**: Ensures 100% code coverage with JaCoCo.
- **Build Verification**: Compiles and builds the application using Maven.

If any of these checks fail, the pipeline will fail, preventing the integration of non-compliant code.

## Running with Docker

You can also run the application as a Docker container.

### 1. Build the Docker Image

First, build the image using the provided `Dockerfile`. This command will create a Docker image named `ddd-with-spring-boot`.

```bash
docker build -t ddd-with-spring-boot .
```

### 2. Run the Docker Container

Next, run the container in detached mode, mapping port `8080` on your local machine to the container's port `8080`.

```bash
docker run -d -p 8080:8080 --name ddd-app ddd-with-spring-boot
```

### 3. Verify the Application

After a few moments, you can verify that the application is running by checking the actuator health endpoint.

```bash
curl http://localhost:8080/actuator/health
```

You should see a response like this:

```json
{"status":"UP"}
```

To view the application logs, you can use the following command:

```bash
docker logs ddd-app
```
