FROM openjdk:17-jdk-alpine
MAINTAINER com.qre
COPY pay/pay_api/target/pay_api-1.0-SNAPSHOT.jar pay_api.jar
ENTRYPOINT ["java", "-jar", "/pay_api.jar"]
EXPOSE 8080
