# Build stage
FROM maven:3.8.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom
COPY pom.xml .

# Pre-fetch dependencies to leverage Docker layer caching
# This step will only rerun if pom.xml changes
RUN --mount=type=cache,target=/root/.m2 mvn dependency:resolve

# Copy the rest of the source code
COPY src ./src

# Build the application using cache mount for Maven repository
# This will drastically speed up subsequent builds by reusing dependencies
RUN --mount=type=cache,target=/root/.m2 mvn package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
