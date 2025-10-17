# Stage 1: Build the JAR
FROM gradle:8.9-jdk21 AS builder
WORKDIR /app

# Copy everything and build the JAR
COPY . .
RUN gradle clean build -x test

# Stage 2: Run the app
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy JAR from the build stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Create non-root user for security
RUN useradd --create-home appuser
USER appuser

# Expose port
EXPOSE 8080

# Run the Spring Boot app
CMD ["java", "-jar", "app.jar"]
