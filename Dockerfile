FROM maven:3.8.4-jdk-8
RUN mkdir -p /usr/src/app/testautomation
WORKDIR /usr/src/app/testautomation
ADD . /usr/src/app/testautomation

RUN mvn clean test



