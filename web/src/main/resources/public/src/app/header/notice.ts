import {DatePipe} from '@angular/common' ;
import {User} from './../user/user';
export class Notice {
    noticeId: number;
    user:User;
    path: string;
    description: string;
    typeNotice:NoticeType;
}
export enum NoticeType{
    TICKET_ASSIGNED,
    TICKET_CREATOR,
    MESSAGE
}