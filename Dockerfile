#########################################
# 1) ETAPA DE BUILD (Maven + OpenJDK17) #
#########################################
FROM maven:3-openjdk-17 AS build
WORKDIR /app

# 1.1 Copiamos pom.xml e instalamos dependencias “offline” para cachear
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 1.2 Copiamos el código fuente y compilamos (sin tests)
COPY src ./src
RUN mvn clean package -DskipTests

#########################################
# 2) ETAPA DE RUNTIME (JRE17 Alpine)    #
#########################################
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# 2.1 Copiamos toda la carpeta fast-jar que ha generado Quarkus
COPY --from=build /app/target/quarkus-app ./quarkus-app

# 2.2 Exponemos el puerto 8080 (el que Quarkus usa por defecto)
EXPOSE 8080

# 2.3 Arrancamos con el jar que contiene el MANIFEST y el classpath ya configurado
CMD ["java", "-jar", "quarkus-app/quarkus-run.jar"]
