FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/project_1410002-0.0.1-SNAMPSHOT.jar.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]