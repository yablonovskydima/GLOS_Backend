FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./notification-service/pom.xml /app/notification-service/pom.xml
COPY ./notification-service/src /app/notification-service/src

WORKDIR /app/notification-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/notification-service/target/*.jar /app/app.jar

EXPOSE 9003

ENTRYPOINT ["java", "-jar", "/app/app.jar"]