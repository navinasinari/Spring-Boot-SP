FROM openjdk:8-jre-slim
EXPOSE 8080
ADD SPproject/target/spring-sp.jar spring-sp.jar
ENTRYPOINT ["java", "-jar","/spring-sp.jar"]