FROM maven:3.9.4-eclipse-temurin-21-alpine

WORKDIR /app
ADD pom.xml /app
RUN mvn verify clean --fail-never

# Image layer: with the application
COPY . /app
RUN mvn -v
RUN mvn clean install -DskipTests
EXPOSE 8080
ADD ./target/NauJava-0.0.1-SNAPSHOT.jar /developments/
ENTRYPOINT ["java", "-jar", "/developments/NauJava-0.0.1-SNAPSHOT.jar"]