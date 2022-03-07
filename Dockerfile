#Uses openjdk 11 image
FROM gcr.io/distroless/java:11
EXPOSE 8081
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} accounts.jar
ENTRYPOINT ["java","-jar","/accounts.jar"]