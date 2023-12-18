FROM openjdk:21-jdk
RUN mkdir /app
COPY ./target/app.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]