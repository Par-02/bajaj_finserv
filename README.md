# Bajaj Finserv Health - Java Qualifier

This is a Spring Boot application that:
1. Sends a POST request to generate a webhook on startup
2. Processes the response to get webhook URL and JWT token
3. Generates a SQL query based on the problem statement
4. Submits the solution to the webhook URL with JWT authentication

## How to Build and Run

1. Build the project:
   ```bash
   mvn clean package
