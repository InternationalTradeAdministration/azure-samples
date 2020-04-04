# Azure Web App for Containers Demo

A sample Node application. The stack doesn't matter for this demo.

## Prerequisites

The following items are needed to build and deploy this application. You may skip installing the Prerequisites for
building & deploying locally if you intend on using Azure DevOps (CI/CD); the Azure prerequisites will still be needed.

### Building Locally

- [NPM](https://www.npmjs.com/get-npm)

### Azure

- Azure Service Plan (commands to create one are below)
- Azure Web App (commands to create one are below)

## Build and Deploy from Local Workstation

### Build

```shell script
npm install
npm build
```

### Build Docker Image

The following environment variables are needed for this script:

- ACR User Name: The name of the azure container registry
- ACR Key: The access key to the azure container registry

```shell script
sudo docker login <acr-username>.azurecr.io -u <acr-username> -p <acr-key>
sudo docker build -t <acr-username>.azurecr.io/ita-azure-app-service-demo .
sudo docker push <acr-username>.azurecr.io/ita-azure-app-service-demo:latest
```

### Deploy as a Web App

This can also be done via the azure portal; below are the commands if using the azure cli.

Choose the appropriate sku for the application, this demo uses the free F1 capasity.
The following variables are needed for this script:

- Azure Resource Group
- Service Plan Name
- ACR User Name: The name of the azure container registry
- ACR Key: The access key to the azure container registry

```shell script
az login
az appservice plan create -g <resource-group> -n sample-web-app-plan --sku F1 --is-linux --location centralus
az webapp create -g <resource-group> -n web-app-demo-itadev2 -p sample-web-app-plan -i <acr-username>.azurecr.io/ita-azure-app-service-demo:latest
az webapp config container set -n web-app-demo-itadev2 -g <resource-group> --docker-custom-image-name <acr-username>.azurecr.io/ita-azure-app-service-demo:latest --docker-registry-server-url https://<azure-container-registry-name>.azurecr.io --docker-registry-server-user <registry-username> --docker-registry-server-password <acr-key>
az webapp start -n web-app-demo-itadev2 -g <resource-group>
```

## Build and Deploy from Azure DevOps (CI/CD)

The file azure-pipeline.yml has the minimum required steps for building the application within a CI pipeline for deployment as an  App Service. Prior to being used, it needs to be updated with the appropriate service connection and container registry.

Creating build and release pipelines may be done through the Azure DevOps portal.

When deployed successfully, the following API will return 'Hello World':

<https://web-app-demo-itadev2.azurewebsites.net/api/hello-world>
