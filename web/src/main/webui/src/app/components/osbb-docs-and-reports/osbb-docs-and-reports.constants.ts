import { Injectable } from '@angular/core';
import { API_URL } from '../../../shared/models/localhost.config';

@Injectable()
export class OsbbDocumentsAndReportsConstants {
  public get default(): any {
    return {
      uploadUrl: API_URL + '/restful/google-drive/upload',
      root: 'appDataFolder',
      ctrlUrl: API_URL + '/restful/google-drive'
    };
  }
}
