import {Injectable} from "@angular/core";
import ApiService = require("./file.location.path");

@Injectable()
export class UrlParserService {
    private url = ApiService.fileDownloadPath;

    constructor(){
    }

   parseUrl(url:string): string {
       return this.url + url.substr(14);
   }
}