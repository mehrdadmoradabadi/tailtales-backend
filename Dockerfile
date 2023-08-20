#
# Build stage
#
FROM maven:3.8.2-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/production-0.0.1-SNAPSHOT.jar fs14-backend.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","production.jar"]