FROM eclipse-temurin:17-jre-alpine
ARG JAR_FILE=target/tool-rent-app-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} tool-rent-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/tool-rent-app-0.0.1-SNAPSHOT.jar"]