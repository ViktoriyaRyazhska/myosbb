import { Apartment } from './apartment.model';
import { House } from './house.model';

export class User {
    public userId: number;
    public firstName: string;
    public lastName: string;
    public birthDate: string;
    public email: string;
    public phoneNumber: string;
    public osbbId: number;
    public gender: string;
    public role: string;
    public activated: boolean;
    public apartment: Apartment;
    public house: House;
}
