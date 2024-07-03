FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./user-manager-service/pom.xml /app/user-manager-service/pom.xml
COPY ./user-manager-service/src /app/user-manager-service/src

WORKDIR /app/user-manager-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/user-manager-service/target/*.jar /app/app.jar

EXPOSE 9005

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
