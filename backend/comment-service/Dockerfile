FROM maven:3.9.6-amazoncorretto-17 AS builder

COPY pom.xml /app/pom.xml
COPY ./comment-service/pom.xml /app/comment-service/pom.xml
COPY ./comment-service/src /app/comment-service/src

WORKDIR /app/comment-service

RUN mvn clean package

FROM amazoncorretto:17

COPY --from=builder /app/comment-service/target/*.jar /app/app.jar

EXPOSE 9007

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
