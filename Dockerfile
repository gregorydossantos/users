FROM ubuntu:latest AS build

RUN apt update
RUN apt install openjdk-21-jdk -y
RUN apt install maven -y

COPY . .

RUN mvn clean install

FROM openjdk:25-ea-21-slim-bullseye
EXPOSE 8081
COPY --from=build /target/api-userEntity-v1.0.0.jar api-userEntity.jar

ENTRYPOINT ["java", "-jar", "api-userEntity.jar"]