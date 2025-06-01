#
# 1) Etapa de build usando Maven (JDK 17)
#
FROM maven:3.8-jdk-17-slim AS build
WORKDIR /app

# Copiamos pom.xml y descargamos dependencias (capa cacheable)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el código fuente y empaquetamos sin tests
COPY src ./src
RUN mvn clean package -DskipTests

#
# 2) Etapa de runtime con la JVM
#
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el JAR “runner” generado por Quarkus
COPY --from=build /app/target/*-runner.jar app.jar

# Exponemos el puerto que usa Quarkus (por defecto 8080)
EXPOSE 8080

# Comando para arrancar la app en modo JVM
CMD ["java", "-jar", "app.jar"]