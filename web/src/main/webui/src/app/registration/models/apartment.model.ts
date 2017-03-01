export interface IApartment{
  apartmentId:number;
  square:number;
  number:number;
  owner:number;
  house:any;
}
export class Apartment implements IApartment{
  apartmentId:number;
  square:number;
  number:number;
  house:any;
  owner:number;

  constructor(apartmentId:number,square:number,number:number,house:any,owner:number, users:any[],bills:any[]) {
    this.apartmentId=apartmentId;
    this.square=square;
    this.number=number;
    this.house=house;
    this.owner=owner;
  }
}

