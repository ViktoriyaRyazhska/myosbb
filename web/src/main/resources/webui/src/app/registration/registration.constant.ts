import { LoginConstants } from '../shared/login/login.constants';

export class RegistrationConstants {
  public static get Masks() {
    return {
      textMask:  [/[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/,
      /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/, /[A-zА-яІ-і]/],
      phoneMask: ['+' , '3', '8' , '(', /[0]/, /\d/, /\d/, ')', /\d/, /\d/, /\d/, '-', /\d/, /\d/, /\d/],
      alphabet: ['a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' ,
      'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' ,
      'v' , 'w' , 'x' , 'y' , 'z' ],
      addressUrl: LoginConstants.Login.serverUrl + '/restful/address',
      allRegions: LoginConstants.Login.serverUrl + '/restful/address/region',
      allCities: LoginConstants.Login.serverUrl + '/restful/address/city/',
      allStreets: LoginConstants.Login.serverUrl + '/restful/address/street/',
      streetById: LoginConstants.Login.serverUrl + '/restful/address/street/id/',
      allDistricts: LoginConstants.Login.serverUrl + '/restful/address/district/',
      districtById: LoginConstants.Login.serverUrl + '/restful/address/district/id/'
    };
  }
}
