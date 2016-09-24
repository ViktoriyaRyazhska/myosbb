import {User} from '../../user/user';

export class Option {
    optionId: number;
    description: string;
    voteId: number;
    users:User[];
    progress: string;
}