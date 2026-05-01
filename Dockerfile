FROM maven:3.9.14-eclipse-temurin-25 AS build
 
WORKDIR /app

COPY wealthmanagement/pom.xml .
COPY wealthmanagement/src ./src

RUN mvn clean package -e

FROM eclipse-temurin:25-jre

WORKDIR /app

COPY --from=build /app/target/wealthmanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
 
CMD ["java", "-jar", "app.jar"]