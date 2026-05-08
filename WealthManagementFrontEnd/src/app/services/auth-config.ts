import { PublicClientApplication } from '@azure/msal-browser';

export const msalInstance = new PublicClientApplication({
  auth: {
    clientId: '1ae58dcb-e998-4c0f-9761-e186cacf4c35', //This might be wrong Needs to be "YOUR_FRONTEND_CLIENT_ID"
    authority: 'https://login.microsoftonline.com/f947665a-e85c-43ab-8198-2b8c8cbeb643',
    redirectUri: window.location.origin
  }
});