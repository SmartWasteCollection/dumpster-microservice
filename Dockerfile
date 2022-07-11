FROM mcr.microsoft.com/azure-cli:latest
COPY ./ /dumpster-microservice/
WORKDIR /dumpster-microservice/

EXPOSE 8080
EXPOSE 27017

COPY bootstrap.sh /bootstrap.sh
RUN chmod +x ./bootstrap.sh
ENTRYPOINT ["bash", "./bootstrap.sh"]
