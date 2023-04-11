# Base image
FROM amazoncorretto:17.0.6-alpine3.17

# Create app directory
WORKDIR /app

# Copy JAR file to the container
COPY target/people-0.0.1-SNAPSHOT.jar app.jar

# Define a variável de ambiente para o perfil de execução
ENV SPRING_PROFILES_ACTIVE=dev

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]
