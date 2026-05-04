docker build -t wealthmanagement:v4 .
docker tag wealthmanagement:v4 apmpacr.azurecr.io/wealthmanagement:v4

az acr login --name apmpacr
docker push apmpacr.azurecr.io/wealthmanagement:v4

az aks get-credentials --resource-group 20260316-EY-Java --name apmp-aks --overwrite-existing

kubectl apply -f .\azure\secret.yaml
kubectl apply -f .\azure\deployment.yaml
kubectl apply -f .\azure\service.yaml

kubectl rollout restart deployment/wealthmanagement