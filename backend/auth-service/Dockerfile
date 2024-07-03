FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./auth-service/pom.xml /app/auth-service/pom.xml
COPY ./auth-service/libs /app/auth-service/libs
COPY ./auth-service/src /app/auth-service/src

WORKDIR /app/auth-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/auth-service/target/*.jar /app/app.jar

EXPOSE 9006

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
