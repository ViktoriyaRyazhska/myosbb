import { HousePage } from "./house-page.model";

export class Apartment{
  apartmentId:number;
  square:number;
  number:number;
  house: HousePage;
  owner:number;

  constructor(
    apartmentId:number,
    square:number,
    number:number,
    house:HousePage,
    owner:number,
    ) {

    this.apartmentId = apartmentId;
    this.square = square;
    this.number = number;
    this.house = house;
    this.owner = owner;
  }
}

