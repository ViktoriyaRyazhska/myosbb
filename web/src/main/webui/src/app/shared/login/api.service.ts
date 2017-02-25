import { API_URL } from '../../../shared/models/localhost.config';
export class ApiService {
  public static serverUrl = API_URL;
  public static AUTH = 'Authorization';
  public static CONTENT_TYPE = 'Content-Type';
  public static APP_JSON = `application/json`;
  public static APP_XWFU = `application/x-www-form-urlencoded`;
  public static ACCEPT = 'Accept';
  public static BASIC_TOKEN = `Basic  Y2xpZW50YXBwOjEyMzQ1Ng==`;
  public static BEARER = 'Bearer ';
}
