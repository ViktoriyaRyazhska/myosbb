/**
 * Created by Oleg on 11.08.2016.
 */
import {HousePageObject} from "../../app/house/house.page.object";
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
    house:HousePageObject;
    owner:number;

    constructor(apartmentId:number,square:number,number:number,house:HousePageObject,owner:number,
                users:any[],bills:any[]) {

        this.apartmentId=apartmentId;

        this.square=square;

        this.number=number;

        this.house=house;

        this.owner=owner;

    }


}

