import { Injectable } from '@angular/core';
import { API_URL } from '../../shared/models/localhost.config';

@Injectable()
export class OsbbConstants {
  public get default(): any {
    return {
      attachmentUploadUrl: API_URL + '/restful/attachment',
      osbbUrl: API_URL + '/restful/osbb'
    };
  }
}
