import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'myCapitalize'})
export class CapitalizeFirstLetterPipe implements PipeTransform {
  public transform (value: any) {
    if (value) {
      return value.charAt(0).toUpperCase() + value.slice(1);
    }
    return value;
  }
}
