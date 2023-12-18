FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/coinlizard-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
