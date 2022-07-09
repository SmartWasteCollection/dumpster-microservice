FROM openjdk:19-alpine
COPY ./ /dumpster-microservice/
WORKDIR /dumpster-microservice/
EXPOSE 8080
EXPOSE 27017
CMD ./gradlew run
