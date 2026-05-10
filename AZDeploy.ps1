# cd WealthManagementFrontEnd
# npm cache clean --force
# ng build --configuration=production --delete-output-path=true 
# cd ..
# Remove-Item -Path ".\wealthmanagement\src\main\resources\static\" -Recurse -Force
# Copy-Item -Path ".\WealthManagementFrontEnd\dist\WealthManagementFrontEnd\browser\*" -Destination ".\wealthmanagement\src\main\resources\static\" -Recurse -Force

docker build --no-cache -t wealthmanagement:v24 .
docker tag wealthmanagement:v24 apmpacr.azurecr.io/wealthmanagement:v24

cd WealthManagementFrontEnd
docker build --no-cache -t wealthmanagement-frontend:v24 .
cd ..
docker tag wealthmanagement-frontend:v24 apmpacr.azurecr.io/wealthmanagement-frontend:v24

az acr login --name apmpacr
docker push apmpacr.azurecr.io/wealthmanagement:v24
docker push apmpacr.azurecr.io/wealthmanagement-frontend:v24

az aks get-credentials --resource-group 20260316-EY-Java --name apmp-aks --overwrite-existing

kubectl apply -f .\azure\secret.yaml
kubectl apply -f .\azure\deployment.yaml
kubectl apply -f .\azure\service.yaml
kubectl apply -f .\azure\ingress.yaml
kubectl apply -f .\azure\cluster-issuer.yaml

kubectl apply -f .\azure\frontend-deployment.yaml
kubectl apply -f .\azure\frontend-service.yaml

kubectl annotate svc -n app-routing-system nginx service.beta.kubernetes.io/azure-dns-label-name="wealthmanagementportal" --overwrite


kubectl rollout restart deployment -n app-routing-system nginx
kubectl rollout restart deployment/wealthmanagement
kubectl rollout restart deployment/wealthmanagement-frontend