FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./access-service/pom.xml /app/access-service/pom.xml
COPY ./access-service/libs /app/access-service/libs
COPY ./access-service/src /app/access-service/src

WORKDIR /app/access-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/access-service/target/*.jar /app/app.jar

EXPOSE 9011

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
