FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./eureka-service/pom.xml /app/eureka-service/pom.xml
COPY ./eureka-service/src /app/eureka-service/src

WORKDIR /app/eureka-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/eureka-service/target/*.jar /app/app.jar

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
