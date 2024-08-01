# Image de base avec OpenJDK
FROM openjdk:17

# Répertoire de travail
WORKDIR /app

# JAR de l'application dans l'image Docker
COPY target/takalobazar-0.0.1-SNAPSHOT.jar app.jar

# Port de l'application
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
