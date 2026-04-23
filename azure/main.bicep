// Reusable params
param appPrefix string = 'apmp'
param tags object = {
  BatchID: '20260316'
  CreatedBy: 'MPham'
}
param location string = 'eastus'

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
    locations:[
      {
        locationName: location
      }
    ]
    databaseAccountOfferType: cosmosAccType
    /*
    apiProperties: {
      serverVersion: 
    }*/
  }
}
