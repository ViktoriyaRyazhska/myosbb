export class UserRegistration {
    public userId: number;
    public firstName: string;
    public lastName: string;
    public birthDate: string;
    public email: string;
    public password: string;
    public phoneNumber: string;
    public osbbId: number;
    public gender: string;
    public activated: boolean;
    public apartmentId: number;
    public role: number;
    public house: number;
    public status: any;
    constructor() {
        this.role = 1;
    }
}
