# sb-transaction-service
Spring Boot Sample Application

mvn clean install

mvn clean package


docker build -f src/main/docker/Dockerfile -t sb-transaction-service .


docker run -p 8081:8081 -t  sb-transaction-service:latest