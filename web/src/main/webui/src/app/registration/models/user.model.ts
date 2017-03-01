import { Apartment } from "./apartment.model";
import { House } from "./house.model";
export class User {
    userId:number;
    firstName:string;
    lastName:string;
    birthDate:string;
    email:string;
    phoneNumber:string;
    osbbId:number;
    gender:string;
    role:string;
    activated:boolean;
    apartment:Apartment;
    house:House;
}
