cd WealthManagementFrontEnd
ng build --delete-output-path=true --configuration
cd ..
Remove-Item -Path ".\wealthmanagement\src\main\resources\static\" -Recurse -Force
Copy-Item -Path ".\WealthManagementFrontEnd\dist\WealthManagementFrontEnd\browser\*" -Destination ".\wealthmanagement\src\main\resources\static\" -Recurse -Force

docker build --no-cache -t wealthmanagement:v5 .
docker tag wealthmanagement:v5 apmpacr.azurecr.io/wealthmanagement:v5

az acr login --name apmpacr
docker push apmpacr.azurecr.io/wealthmanagement:v5

az aks get-credentials --resource-group 20260316-EY-Java --name apmp-aks --overwrite-existing

kubectl apply -f .\azure\secret.yaml
kubectl apply -f .\azure\deployment.yaml
kubectl apply -f .\azure\service.yaml

kubectl rollout restart deployment/wealthmanagement

