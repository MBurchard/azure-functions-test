# ComSTAR Azure Functions

Azure Functions for ComSTAR Project

## Setup Environment

 - https://docs.microsoft.com/de-de/azure/azure-functions/functions-create-first-java-gradle
 - https://docs.microsoft.com/de-de/cli/azure
 - https://docs.microsoft.com/de-de/azure/azure-functions/functions-run-local#v2

## Azure problems

Microsoft unfortunately does not manage to use the correct syntax for variables on Linux

 - https://stackoverflow.com/questions/64032219/azure-function-in-java-does-not-work-locally

Change the `worker.config.json` with `sudo vim /usr/lib/azure-functions-core-tools-3/workers/java/worker.config.json`.

Find the Windows style variable `%JAVA_HOME%` and change to the absolut path, because the Linux style variable `$JAVA_HOME` does not work either.

## Logs

```
az login
az account list
az account set --subscription UEPREPROD01
az webapp log tail --resource-group UCCS-PaaS-uat-rgp-001 --name comstar-azure-functions
```
