# dumpster-microservice

![build](https://img.shields.io/github/workflow/status/SmartWasteCollection/dumpster-microservice/Build%20and%20Test) [![codecov](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice/branch/main/graph/badge.svg?token=AGJH4U1WOG)](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice) ![GitHub](https://img.shields.io/github/license/SmartWasteCollection/dumpster-microservice)

This repository contains the microservice that handles the generation and management of Collection Points and Dumpsters digital twins.

## Usage
You can run the microservice using a docker container with the command
```
docker run -p 3000:8080 --env-file .env -it ghcr.io/smartwastecollection/dumpster-microservice:<latest-tag>
```
> Note: You can remove `-it` flags and use `-d` to launch it as a daemon

The `.env` file **must** contain the secrets needed to perform the login into the azure cloud platform:
1. `AZURE_SERVICE_PRINCIPAL_NAME`: UUID that represents the Application (client) ID of the _Service Principal_
2. `AZURE_SECRET`: UUID that identifies the secret used to perform the login from the _Service Principal_  
3. `AZURE_TENANT`: UUID that identifies the Directory (tenant) ID of the _Service Principal_
