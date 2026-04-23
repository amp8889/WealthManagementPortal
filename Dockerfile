FROM maven:3.9.14-openjdk-25-alpine AS build
 
WORKDIR /app

COPY wealthmanagement/pom.xml .
COPY wealthmanagement/src ./src

RUN mvn package

FROM openjdk:25

WORKDIR /app

COPY --from=build /app/target/WealthmanagementApplication.jar .

EXPOSE 3000
 
CMD ["java","-jar","WealthmanagementApplication.jar"]