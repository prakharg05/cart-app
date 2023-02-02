FROM openjdk:17-oracle
COPY target/cartapp*.jar cartapp-server-1.0.0.jar
ENTRYPOINT ["java","-jar","/cartapp-server-1.0.0.jar"]
