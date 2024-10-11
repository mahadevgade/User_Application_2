

FROM openjdk:17

COPY target/usrmgmt.jar usrmgmt.jar

EXPOSE 9090 

ENTRYPOINT ["java", "-jar", "usrmgmt.jar"]  