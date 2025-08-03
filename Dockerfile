# ----------------------------
# Stage 1: Build the Spring Boot app using Maven
# ----------------------------
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the project files
COPY . .

# Build the project and skip tests
RUN mvn clean package -DskipTests

# ----------------------------
# Stage 2: Run the Spring Boot app
# ----------------------------
FROM eclipse-temurin:21-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the previous stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port Spring Boot uses
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar","app.jar"]