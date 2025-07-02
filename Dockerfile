FROM ubuntu:latest

RUN apt update
RUN apt install openjdk-21-jdk -y
RUN apt install maven -y

WORKDIR /app

COPY . .

RUN mvn clean install

COPY /target/api-users-1.jar users.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "users.jar"]