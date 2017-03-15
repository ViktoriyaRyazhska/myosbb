import { ApartmentRegistration } from "./apartment.registration.model";
import { UserRegistrationDTO } from "./userRegistrationByAdminDto.model";

export class UserApartment {
    apartment: ApartmentRegistration;
    userRegitrationByAdminDTO:UserRegistrationDTO;
    
    constructor() {
        this.apartment = new ApartmentRegistration();
        this.userRegitrationByAdminDTO = new UserRegistrationDTO();
    }
}


