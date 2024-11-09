FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build app/target/tp-foyer-5.0.0.jar tp-foyer-5.0.0.jar
ENTRYPOINT ["java","-jar","/app/tp-foyer-5.0.0.jar"]
