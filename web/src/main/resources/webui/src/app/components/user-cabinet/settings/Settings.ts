import { User }  from '../../../models/user.model';

export class Settings {
    public settingsId: number;
    public user: User;
    public assigned: boolean;
    public creator: boolean;
    public comment: boolean;
    public answer: boolean;
}
