# Use lightweight Java image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy jar file
COPY target/*.jar app.jar

# Expose service port (change accordingly)
EXPOSE 8081

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
