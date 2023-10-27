FROM gradle:8.4-jdk
 #AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
RUN apt-get -y update
RUN apt-get -y install git

RUN git clone https://github.com/cankill/kflibot.git
RUN ls -la kflibot
WORKDIR $APP_HOME/kflibot
RUN ls -la .
RUN chmod +x gradlew
RUN ./gradlew clean shadowJar

## actual container
#FROM eclipse-temurin:17-jdk-jammy
#ENV ARTIFACT_NAME=kflibot-1.0-SNAPSHOT-all.jar
#ENV APP_HOME=/usr/app
#
#WORKDIR $APP_HOME
##COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
#ENTRYPOINT exec java -jar ${ARTIFACT_NAME}