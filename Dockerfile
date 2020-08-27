# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
LABEL maintainer="sandeep.kandala@advance-auto.com"

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8080 available to the world outside this container
EXPOSE 8080

# The application's jar file
ARG JAR_FILE=target/alexa-orders.jar

# Add the application's jar to the container
ADD ${JAR_FILE} alexa-orders.jar

# Run the jar file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandomx`","-jar","/alexa-orders.jar"]
