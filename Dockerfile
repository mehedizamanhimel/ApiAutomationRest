FROM maven:3.9.6-eclipse-temurin-11
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app
COPY . /usr/src/app
RUN mvn clean test
