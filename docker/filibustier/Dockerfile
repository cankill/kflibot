FROM gradle:8.4-jdk AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
RUN git clone https://github.com/cankill/kflibot.git
WORKDIR $APP_HOME/kflibot
RUN chmod +x gradlew
RUN ./gradlew clean shadowJar

# actual container
FROM eclipse-temurin:17.0.8.1_1-jre-ubi9-minimal
ENV ARTIFACT_NAME=kflibot-1.0-SNAPSHOT-all.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/kflibot/build/libs/$ARTIFACT_NAME .
ENTRYPOINT exec java -jar ${ARTIFACT_NAME}