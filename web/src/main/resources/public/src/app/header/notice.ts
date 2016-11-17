import { DatePipe } from '@angular/common' ;
import { User } from './../user/user';
export class Notice {
    noticeId:number;
    user:User;
    name:string;
    description:string;
    time:Date;
    path:string;
    typeNotice:NoticeType;

}
export enum NoticeType{
    TO_ASSIGNED,
    TO_CREATOR,
    MESSAGE,
    ANSWER
}