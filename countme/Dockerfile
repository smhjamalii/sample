FROM oracle/graalvm-ce:20.0.0-java11 AS graalvm
WORKDIR /opt/graalvm
RUN gu install native-image
ENTRYPOINT ["native-image"]

FROM maven:3.6.3-jdk-11-openj9 AS maven
WORKDIR /usr/app/
ENV APP_HOME="/usr/app/"
COPY code-with-quarkus/src $APP_HOME/src
COPY code-with-quarkus/pom.xml $APP_HOME/
COPY --from=graalvm /opt/graalvm-ce-java11-20.0.0 /opt/graalvm-ce-java11-20.0.0
ENV JAVA_HOME="/opt/graalvm-ce-java11-20.0.0/"
ENV PATH="$PATH:$JAVA_HOME/bin"
RUN apt-get update
RUN apt-get -y install gcc libc6-dev zlib1g-dev curl bash
#RUN apt-get install libz-dev zlib1g-dev
RUN mvn -f $APP_HOME/pom.xml package -Pnative
RUN cp $APP_HOME/target/*-runner $APP_HOME/target/application
EXPOSE 8282
CMD ["./target/application", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=8282"]
