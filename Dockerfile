FROM openjdk:17-jdk

LABEL authors="RLS"

WORKDIR /app

EXPOSE 8080

CMD ["java", "jar"]
