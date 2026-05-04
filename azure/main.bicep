// Reusable params
param appPrefix string = 'apmp'
param tags object = {
  BatchID: '20260316'
  CreatedBy: 'MPham'
}
param location string = 'eastus'
param appName string = '${appPrefix}wealthmanagement'

// CosmosDB Account params
param cosmosAccName string = '${appPrefix}-cosmos-account'
param cosmosAccType string = 'Standard'
param cosmosAccKind string = 'MongoDB' // Use MongoDB API

resource cosmosDBAccount 'Microsoft.DocumentDB/databaseAccounts@2025-10-15' = {
  name: cosmosAccName
  location: location
  tags: tags
  kind: cosmosAccKind
  properties: {
    locations: [
      {
        locationName: location
      }
    ]
    databaseAccountOfferType: cosmosAccType
    apiProperties: {
      serverVersion: '7.0'
    }
  }
}

param cosmosDBname string = '${appPrefix}-cosmos'

resource cosmosDB 'Microsoft.DocumentDB/databaseAccounts/mongodbDatabases@2025-10-15' = {
  name: cosmosDBname
  tags: tags
  parent: cosmosDBAccount
  properties: {
    resource: {
      id: cosmosDBname
    }
  }
}

param acrName string = '${appPrefix}acr'
param acrSku string = 'Basic'

resource acr 'Microsoft.ContainerRegistry/registries@2025-11-01' = {
  name: acrName
  tags: tags
  location: location
  sku: {
    name: acrSku
  }
}

param aksName string = '${appPrefix}-aks'
param aksVmSize string = 'Standard_D2s_v3'

resource aks 'Microsoft.ContainerService/managedClusters@2026-01-01' = {
  name: aksName
  tags: tags
  location: location
  properties: {
    dnsPrefix: appName
    agentPoolProfiles: [
      {
        name: appPrefix
        count: 2
        mode: 'System'
        vmSize: aksVmSize
      }
    ]
  }
  identity: {
    type: 'SystemAssigned'
  }
}

// https://learn.microsoft.com/en-us/azure/role-based-access-control/built-in-roles
var acrPushRoleId = subscriptionResourceId(
  'Microsoft.Authorization/roleDefinitions',
  '8311e382-0749-4cb8-b61a-304f252e45ec'
)

// Add deployer's ACRPush to the ACR
resource acrPushDeployer 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(acr.id, deployer().objectId, acrPushRoleId)
  scope: acr
  properties: {
    roleDefinitionId: acrPushRoleId
    principalId: deployer().objectId
    principalType: 'User'
  }
}

var acrPullRoleId = subscriptionResourceId(
  'Microsoft.Authorization/roleDefinitions',
  '7f951dda-4ed3-4680-a7ca-43fe172d538d'
)

resource acrPullAKS 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(aks.id, acr.id, acrPullRoleId)
  scope: acr
  properties: {
    roleDefinitionId: acrPullRoleId
    principalId: aks.identity.principalId
  }
  
}

resource acrPullAKSAgentPool 'Microsoft.Authorization/roleAssignments@2022-04-01' = {
  name: guid(aks.id, acr.id, acrPullRoleId, 'agentpool')
  scope: acr
  properties: {
    roleDefinitionId: acrPullRoleId
    principalId: aks.properties.identityProfile.kubeletidentity.objectId
  }
}

