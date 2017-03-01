import { API_URL } from '../models/localhost.config';

export class UrlListConfig {
  public static URL_LIST = {
    pageUrl: `${API_URL}/restful/ticket/page`,
    stateUrl: `${API_URL}/restful/ticket/state`,
    assignUrl: `${API_URL}/restful/ticket/user`,
    billUrl: `${API_URL}/restful/bill/?status=`,
    idUrl: `${API_URL}/restful/bill/`,
    contractsUrl: `${API_URL}/restful/contract?pageNum=1&&actv=false`,
    contactNameUrl: `${API_URL}/restful/contract/`
  };
}
