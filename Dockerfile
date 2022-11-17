FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ADD target/api-1.0.0.jar api-1.0.0.jar
ENTRYPOINT ["java","-jar","/api-1.0.0.jar"]