FROM openjdk:17-jdk-slim
COPY target/shorten-url-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]