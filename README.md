# dumpster-microservice

[![Continuous Integration](https://github.com/SmartWasteCollection/dumpster-microservice/actions/workflows/build-and-test.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/dumpster-microservice/actions/workflows/build-and-test.yml)
[![Continuous Integration](https://github.com/SmartWasteCollection/dumpster-microservice/actions/workflows/checkout-and-release.yml/badge.svg?event=push)](https://github.com/SmartWasteCollection/dumpster-microservice/actions/workflows/checkout-and-release.yml)
[![GitHub issues](https://img.shields.io/github/issues-raw/SmartWasteCollection/dumpster-microservice?style=plastic)](https://github.com/SmartWasteCollection/dumpster-microservice/issues)
[![GitHub pull requests](https://img.shields.io/github/issues-pr-raw/SmartWasteCollection/dumpster-microservice?style=plastic)](https://github.com/SmartWasteCollection/dumpster-microservice/pulls)
[![GitHub](https://img.shields.io/github/license/SmartWasteCollection/dumpster-microservice?style=plastic)](/LICENSE)
[![GitHub release (latest SemVer including pre-releases)](https://img.shields.io/github/v/release/SmartWasteCollection/dumpster-microservice?include_prereleases&style=plastic)](https://github.com/SmartWasteCollection/dumpster-microservice/releases)
[![codecov](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice/branch/main/graph/badge.svg?token=DFXD6WEUFK)](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice)

This repository contains the microservice that handles the generation and management of Collection Points and Dumpsters digital twins.

## Deploy
You can run the microservice using a docker container with the command
```
docker run -p 3000:8080 --env-file .env -it ghcr.io/smartwastecollection/dumpster-microservice:<latest-tag>
```
> Note: You can remove `-it` flags and use `-d` to launch it as a daemon

The `.env` file **must** contain the secrets needed to perform the login into the azure cloud platform:
1. `AZURE_SERVICE_PRINCIPAL_NAME`: UUID that represents the Application (client) ID of the _Service Principal_
2. `AZURE_SECRET`: UUID that identifies the secret used to perform the login from the _Service Principal_  
3. `AZURE_TENANT`: UUID that identifies the Directory (tenant) ID of the _Service Principal_

## Usage
Detailed description about the API [here](https://app.swaggerhub.com/apis/anitvam/dumpster_microservice_api/v1.1.0)
