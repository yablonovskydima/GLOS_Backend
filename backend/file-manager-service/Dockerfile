FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./file-manager-service/pom.xml /app/file-manager-service/pom.xml
COPY ./file-manager-service/libs /app/file-manager-service/libs

COPY ./file-manager-service/src /app/file-manager-service/src

WORKDIR /app/file-manager-service

RUN mvn clean package -DskipTests

FROM amazoncorretto:17

COPY --from=builder /app/file-manager-service/target/*.jar /app/app.jar

EXPOSE 9010

ENTRYPOINT ["java", "-jar", "/app/app.jar"]