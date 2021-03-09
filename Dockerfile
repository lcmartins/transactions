FROM gradle:6.8.2 as builder

USER root

ENV APP_DIR /app

WORKDIR $APP_DIR

COPY build.gradle.kts $APP_DIR/

COPY settings.gradle.kts $APP_DIR/

RUN gradle dependencies

COPY . $APP_DIR

RUN gradle build -x test


USER guest
FROM openjdk:11-jdk-oracle

WORKDIR /app
ARG JAR_FILE=/app/build/libs/*.jar
COPY --from=builder ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
