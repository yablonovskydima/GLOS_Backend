FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./operation-service/pom.xml /app/operation-service/pom.xml
COPY ./operation-service/src /app/operation-service/src

WORKDIR /app/operation-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/operation-service/target/*.jar /app/app.jar

EXPOSE 9004

ENTRYPOINT ["java", "-jar", "/app/app.jar"]