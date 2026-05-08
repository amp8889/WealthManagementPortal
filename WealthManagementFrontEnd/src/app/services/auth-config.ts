import { PublicClientApplication } from '@azure/msal-browser';

export const msalInstance = new PublicClientApplication({
  auth: {
    clientId: '79658250-737d-4b88-b080-a7c8bf2a1d5e', //This might be wrong Needs to be "YOUR_FRONTEND_CLIENT_ID"
    authority: 'https://login.microsoftonline.com/f947665a-e85c-43ab-8198-2b8c8cbeb643',
    redirectUri: window.location.origin
  }
});