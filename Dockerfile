# docker build -t socol-community:v0.0.1 .
FROM maven:3.6.3-jdk-11-openj9 as build

ENV APP_NAME app
ENV APP_HOME /home/app
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

WORKDIR $APP_HOME

COPY pom.xml .
COPY app/pom.xml $APP_HOME/app/pom.xml

RUN mvn dependency:go-offline

COPY app/src $APP_HOME/app/src

RUN mvn package -Dmaven.test.skip=true

FROM openjdk:11-slim as prod

ENV APP_NAME app
ENV APP_HOME /home/app
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

WORKDIR $APP_HOME

COPY --from=build /home/app/java-backend/target/*.jar $APP_HOME/libs/
COPY --from=build /home/app/socol-api/target/*.jar $APP_HOME/libs/
COPY --from=build /home/app/app/target/*.jar $APP_HOME/app.jar

COPY ./startup.sh $APP_HOME/

RUN useradd -ms /bin/bash app && chmod 755 startup.sh

USER app

ENTRYPOINT ["bash","./startup.sh"]
