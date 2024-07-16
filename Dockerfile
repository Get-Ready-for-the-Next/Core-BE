# Stage 1: Build the application
FROM gradle:8.8-jdk22 AS build

# Set the working directory
WORKDIR /home/gradle/project

# Copy the project files
COPY . .

# Clear the Gradle cache
RUN gradle clean

# Build the application
RUN gradle --no-daemon bootJar

# Stage 2: Create the runtime image
FROM amazoncorretto:22-alpine3.16-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /home/gradle/project/build/libs/app.jar /app/app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-Dspring.profiles.active=local", "-jar", "/app/app.jar"]
