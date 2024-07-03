FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./api-gateway/pom.xml /app/api-gateway/pom.xml
COPY ./api-gateway/src /app/api-gateway/src

WORKDIR /app/api-gateway

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/api-gateway/target/*.jar /app/app.jar

EXPOSE 9002

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
