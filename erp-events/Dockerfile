FROM openjdk:17-slim AS build
RUN apt-get update && apt-get install -y maven && apt-get clean
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
FROM gcr.io/distroless/java17
WORKDIR /app

# Etiquetas descriptivas de la imagen
LABEL app.name="@project.name@" \
      app.description="@project.description@" \
      app.version="@project.version@" \
      app.url="http://localhost:8080" \
      app.dev-name="Mateo" \
      app.dev-email="113207@tecnicatura.frc.utn.edu.ar"

COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
CMD []