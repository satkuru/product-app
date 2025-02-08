FROM openjdk:21-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/product-app-*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]