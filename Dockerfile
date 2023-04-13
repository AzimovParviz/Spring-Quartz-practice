FROM openjdk:17-alpine

FROM maven:3.8-openjdk-18-slim

VOLUME /tmp

WORKDIR spring

COPY . .

RUN mvn clean install

RUN mvn test

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]


