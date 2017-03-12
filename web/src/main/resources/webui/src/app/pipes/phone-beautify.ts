import {Pipe, PipeTransform} from "@angular/core";

@Pipe({name: 'phone_number'})
export class BeautifyPhonePipe implements PipeTransform {

  transform(value:any) {
    if (value && value.length == 12) {
        var tmp: string;
        tmp= "+" + value.substr(0,2) + "(" + value.substr(2,3)  +")" + value.substr(5,3) +"-" + value.substr(8)  ;
      return tmp;
    }
    return value;
  }

}
