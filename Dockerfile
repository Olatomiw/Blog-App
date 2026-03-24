FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app

# Copy pom first and resolve deps — this layer gets cached
# as long as pom.xml doesn't change
COPY pom.xml .
RUN mvn dependency:go-offline -q

COPY src ./src
RUN mvn package -DskipTests -q

# Stage 2 — JRE only, not JDK
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
