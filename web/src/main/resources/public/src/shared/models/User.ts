import assign = require("core-js/library/fn/object/assign");
import {Apartment} from "./apartment.interface.ts";
export class User {
    userId:number;
    firstName:string;
    lastName:string;
    birthDate:string;
    email:string;
    phoneNumber:string;
    gender:string;
    password:number;
    activated:boolean;
    role:string;
    osbbId:number;
    apartment:Apartment;

    constructor() {
        this.userId = new Number();
        this.firstName = new String();
        this.lastName = new String();
        this.birthDate = new String();
        this.email = new String();
        this.phoneNumber = new String();
        this.gender = new String();
        this.password = new Number();
        this.activated = new Boolean();
        this.role = new String();
        this.osbbId= new Number();
    }
}