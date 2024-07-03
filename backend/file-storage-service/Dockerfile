FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./file-storage-service/pom.xml /app/file-storage-service/pom.xml
COPY ./file-storage-service/libs /app/file-storage-service/libs
COPY ./file-storage-service/src /app/file-storage-service/src

WORKDIR /app/file-storage-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/file-storage-service/target/*.jar /app/app.jar

EXPOSE 9009

ENTRYPOINT ["java", "-jar", "/app/app.jar"]