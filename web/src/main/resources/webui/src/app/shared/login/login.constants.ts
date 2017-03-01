import { API_URL } from '../../../shared/models/localhost.config.ts';

export class LoginConstants {
  public static get Login(){
    return {
      serverUrl: API_URL,
      auth: 'Authorization',
      contentType: 'Content-Type',
      appJson: 'application/json',
      appXwfu: 'application/x-www-form-urlencoded',
      accept: 'Accept',
      basicToken: 'Basic  Y2xpZW50YXBwOjEyMzQ1Ng==',
      bearer: 'Bearer'
    };
  }
}
