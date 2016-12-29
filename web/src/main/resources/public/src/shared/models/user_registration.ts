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
    house:number;

    constructor() {
        this.role = 1;
    }
}
