FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS builder

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM docker.io/library/eclipse-temurin:21-jdk-alpine AS runner

#ARG USER_NAME=advshop
#ARG USER_UID=1000
#ARG USER_GID=${USER_UID}

ARG PRODUCTION
ARG JDBC_DATABASE_PASSWORD
ARG JDBC_DATABASE_URL
ARG JDBC_DATABASE_USERNAME

ENV PRODUCTION ${PRODUCTION}
ENV JDBC_DATABASE_PASSWORD ${JDBC_DATABASE_PASSWORD}
ENV JDBC_DATABASE_URL ${JDBC_DATABASE_URL}
ENV JDBC_DATABASE_USERNAME ${JDBC_DATABASE_USERNAME}


#RUN addgroup -g ${USER_GID} ${USER_NAME} \
#    && adduser -h /opt/eshop -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}
#
#USER ${USER_NAME}
#WORKDIR /opt/eshop
#COPY --from=builder --chown=${USER_UID}:${USER_GID} /src/eshop/build/libs/*.jar app.jar

#EXPOSE 8080
#ENTRYPOINT ["java"]
#CMD ["-jar", "app.jar"]

WORKDIR /app
COPY mcs-authentication-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java","-jar","mcs-authentication-0.0.1-SNAPSHOT.jar"]