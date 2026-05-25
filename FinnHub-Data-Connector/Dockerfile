# Build optimized image for compilation
FROM maven:3.9.11-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package -DskipTests -B -o

# Build optimized image for runtime
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app

COPY --from=build /app/target/*.jar FinnHubDataConnector.jar

ENTRYPOINT ["java", "-jar", "FinnHubDataConnector.jar"]