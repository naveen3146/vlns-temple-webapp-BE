# Use an official JDK image
FROM openjdk:17-jdk-slim

# Add a volume (optional for caching layers)
VOLUME /tmp

# Add the JAR (change target/*.jar to match your actual jar file)
COPY build/libs/vlnstempleweb-0.0.1-SNAPSHOT.jar /vlnstempleweb-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8085

# Run the jar
ENTRYPOINT ["java","-jar","/vlnstempleweb-0.0.1-SNAPSHOT.jar"]
