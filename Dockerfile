FROM maven:3.9.3-eclipse-temurin-11-alpine 

COPY src /home/fakestoreapi/src
COPY pom.xml /home/fakestoreapi/pom.xml
COPY testng-xml-files /home/fakestoreapi/testng-xml-files
WORKDIR /home/fakestoreapi

ENV fileName=testng.xml

ENTRYPOINT mvn clean test -DfileName=${fileName}
