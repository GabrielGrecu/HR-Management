# Multimaven Project with Docker Compose

# Commands for running the project

To run this project follow these steps:

1. Compile and build Docker images for the applications:

# mvn clean package spring-boot:build-image

Description: this command does the following steps:

## mvn clean:cleans all previously generated files in the project
## mvn package:compiles the source code and creates the application's JAR file
## spring-boot:build-image: builds the Docker image for the application using Spring Boot

2. Start the services defined in docker-compose.yml:

# docker compose up -d

Description: This command starts the services and the containers run in the background

The containers should be up and running and accessible at the specified ports :
- 8080 for candidate-management
- 8081 for user-management) 
- 3307 for mysql candidates-database
- 3308 for mysql users-database

# Optional

3. You can stop the Docker Compose services:

# docker compose down

4. You can run the user-management container separately:

# docker run -p 8081:8081 user-management:0.0.1-SNAPSHOT

5. You can run the candidate-management container separately with port mapping:

# docker run -p 8080:8080 candidate-management:0.0.1-SNAPSHOT

6. You can run the candidate-management container separately without port mapping:

# docker run candidate-management:0.0.1-SNAPSHOT

7. You can list available Docker images:

# docker image ls

8. You can list running Docker containers:

# docker ps