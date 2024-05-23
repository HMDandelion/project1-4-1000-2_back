FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} project_1410002-0.0.1-SNAMPSHOT.jar
ENTRYPOINT ["java","-jar","/app.jar"]