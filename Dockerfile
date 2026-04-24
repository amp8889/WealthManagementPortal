FROM maven:3.9.14-eclipse-temurin-25 AS build
 
WORKDIR /app

COPY wealthmanagement/pom.xml .
COPY wealthmanagement/src ./src

RUN mvn package

FROM openjdk:25-ea

WORKDIR /app

COPY --from=build /app/target/wealthmanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 3000
 
CMD ["java", "-jar", "app.jar"]