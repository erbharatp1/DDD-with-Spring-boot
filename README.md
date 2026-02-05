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
