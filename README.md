# dumpster-microservice

![build](https://img.shields.io/github/workflow/status/SmartWasteCollection/dumpster-microservice/Build%20and%20Test) [![codecov](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice/branch/main/graph/badge.svg?token=AGJH4U1WOG)](https://codecov.io/gh/SmartWasteCollection/dumpster-microservice) ![GitHub](https://img.shields.io/github/license/SmartWasteCollection/dumpster-microservice)

This repository contains the microservice that handles the generation and management of Collection Points and Dumpsters digital twins.

## Usage

To use this microservice you can download the latest release and launch it with
```
./gradlew bootRun
```

Or you can download the latest package and launch it with the command
```
docker run -p 3000:8080 -it ghcr.io/smartwastecollection/dumpster-microservice:<latest-tag>
```
> Note: You can remove `-it` flags and use `-d` to launch it as a daemon
