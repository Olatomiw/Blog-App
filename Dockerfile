FROM maven:3.8-openjdk-17 AS builder
LABEL authors="RLS"
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn package -DskipTests




#Stage 2
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
