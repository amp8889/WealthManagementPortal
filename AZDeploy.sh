docker build -t wealthmanagement:v2 .
docker tag wealthmanagement:v2 apmpacr.azurecr.io/wealthmanagement:v2
docker push apmpacr.azurecr.io/wealthmanagement:v2

az aks get-credentials --resource-group 20260316-EY-Java --name apmp-aks --overwrite-existing

kubectl apply -f .\azure\secret.yaml
kubectl apply -f .\azure\deployment.yaml
kubectl apply -f .\azure\service.yaml