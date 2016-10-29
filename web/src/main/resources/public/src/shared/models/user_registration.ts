import assign = require("core-js/library/fn/object/assign");
export class UserRegistration {
    userId:number;
    firstName:string;
    lastName:string;
    birthDate:string;
    email:string;
    password:string;
    phoneNumber:string;
    osbbId:number;
    gender:string;
    activated:boolean;
    apartmentId:number;
    role:number;

    constructor() {
        // this.userId = new Number();
        // this.firstName = new String();
        // this.lastName = new String();
        // this.birthDate = new String();
        // this.email = new String();
        // this.phoneNumber = new String();
        // this.gender = new String();
        // this.activated = new Boolean();
        // this.apartmentId = new Number();
        // this.osbbId= new Number();
        this.role = 1;
    }
}