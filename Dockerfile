FROM openjdk:21-jdk
WORKDIR /app
COPY target/coinlizard-0.0.1.jar coinlizard-0.0.1.jar
ENTRYPOINT ["java","-jar","coinlizard-0.0.1.jar"]