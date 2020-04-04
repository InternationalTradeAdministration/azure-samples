# Demo Application Deployment to Azure Kubernetes Service

A sample SpringBoot application in Kotlin. The stack doesn't matter for this demo

## Prerequisites

The following items are needed to build and deploy this application. You may skip installing the Prerequisites for
building & deploying locally if you intend on using Azure DevOps (CI/CD); the Azure prerequisites will still be needed.

### Building Locally

- Java 8
- Gradle
- [KUBECTL CLI](https://kubernetes.io/docs/tasks/tools/install-kubectl)

### Azure

- Azure Resource Group
- Azure Container Registry
- DNS Zone configured with a domain
- AKS Cluster with the following:
  - A namespace for the application to live in; for this demo it's: **mdsnamespace**
  - [Ingress Controller](https://docs.microsoft.com/en-us/azure/aks/ingress-basic)
  - [External DNS for Kubernetes](https://github.com/kubernetes-sigs/external-dns/blob/master/docs/tutorials/azure.md)
- You may skip installing External DNS for Kubernetes, but you'll need to manually configure a DNS Zone
- You may also skip creating a DNS Zone by directly registering the public IP of the Ingress Controller with a DNS registrar

## Build and Deploy from Local Workstation

### Build

gradle build

### Build Docker Image

The following environment variables are needed for this script:

- AZURE_CONTAINER_USER: The name of the azure container registry
- AZURE_CONTAINER_KEY: The access key to the azure container registry

```shell script
sudo docker login $AZURE_CONTAINER_USER.azurecr.io -u $AZURE_CONTAINER_USER -p $AZURE_CONTAINER_KEY
sudo docker build -t $AZURE_CONTAINER_USER.azurecr.io/ita-azure-aks-demo .
sudo docker push $AZURE_CONTAINER_USER.azurecr.io/ita-azure-aks-demo:latest
```

### Deploy to AKS

```shell script
kubectl delete deployment,service,ingress ita-azure-aks-demo -n mdsnamespace --ignore-not-found
kubectl apply -f kube-config.yml -n mdsnamespace
```

### Browse Kubernetes Cluster

```shell script
az aks browse --resource-group <azure-resource-group> --name <aks-cluster-name>
```

When deployed successfully, the follow API will return a 'Hello World':
<https://aks-demo-itadev2.vangos-cloudapp.us/api/hello-world>

## Build and Deploy from Azure DevOps (CI/CD)

The file azure-pipeline.yml has the minimum required steps for building the application within a CI pipeline.
Prior to being used, it needs to be updated with the appropriate service connections and container registry.
