FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./database-api-service/pom.xml /app/database-api-service/pom.xml
COPY ./database-api-service/libs /app/database-api-service/libs
COPY ./database-api-service/src /app/database-api-service/src

WORKDIR /app/database-api-service

RUN mvn clean package -DskipTests

FROM amazoncorretto:17

COPY --from=builder /app/database-api-service/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
