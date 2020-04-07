# Azure Kubernetes Services Demo

A sample SpringBoot application in Kotlin. The stack doesn't matter for this demo

## Prerequisites

The following items are needed to build and deploy this application. You may skip installing the Prerequisites for
building & deploying locally if you intend on using Azure DevOps (CI/CD); the Azure prerequisites will still be needed.

### Building Locally

- Java 8
- Gradle
- [KUBECTL CLI](https://kubernetes.io/docs/tasks/tools/install-kubectl)

### Azure

- DNS Zone configured with a domain
- AKS Cluster with the following:
  - A namespace for the application to live in
  - [Ingress Controller](https://docs.microsoft.com/en-us/azure/aks/ingress-basic)
  - [External DNS for Kubernetes](https://github.com/kubernetes-sigs/external-dns/blob/master/docs/tutorials/azure.md)
- You may skip installing External DNS for Kubernetes, but you'll need to manually configure a DNS Zone
- You may also skip creating a DNS Zone by directly registering the public IP of the Ingress Controller with a DNS registrar

## Build and Deploy from Local Workstation

### Build

```shell script
gradle build
```

### Build Docker Image

The following variables are needed for this script:

- ACR User Name: The name of the azure container registry
- ACR Key: The access key to the azure container registry

```shell script
sudo docker login <acr-username>.azurecr.io -u <acr-username> -p <acr-key>
sudo docker build -t <acr-username>.azurecr.io/ita-azure-aks-demo .
sudo docker push <acr-username>.azurecr.io/ita-azure-aks-demo:latest
```

### Deploy to AKS

The following variables are needed for this script:

- Azure Resource Group
- Kuberneted Namespace

```shell script
kubectl delete deployment,service,ingress ita-azure-aks-demo -n <k8-namespace> --ignore-not-found
kubectl apply -f kube-config.yml -n <k8-namespace>
```

### Browse Kubernetes Cluster

```shell script
az aks browse --resource-group <resource-group> --name <cluster-name>
```

### Environment Variables

Some applications may need environment variables; see that projects README for guidance.
For automated deployments, variables will need to be included as hardcoded values, or as tokens in the kube-config.yml. Use tokens for access keys that need to remain private. There are some examples in this project, for more information see this [documentation](https://github.com/qetza/vsts-replacetokens-task#readme).

## Build and Deploy from Azure DevOps (CI/CD)

The file azure-pipeline.yml has the minimum required steps for building the application within a CI pipeline for deployment to AKS (including running tests). Prior to being used, it needs to be updated with the appropriate service connection and container registry.

Creating build and release pipelines may be done through the Azure DevOps portal.

A Service Principle should be used for automated deployments and it needs to have:

- AcrPull permissions on the Container Registry
- Contributor permissions to the Resource Group

When deployed successfully, the following API will return 'Hello World':

<https://aks-demo-itadev2.vangos-cloudapp.us/api/hello-world>
