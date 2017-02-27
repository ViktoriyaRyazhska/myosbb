import { Component, Input, OnInit } from '@angular/core';
import { EventsService } from '../events/events.service';
import { LoginService } from '../../shared/login/login.service';
import { CalendarConstants } from './calendar.const';
import {
  Http,
  Response
} from '@angular/http';
@Component({
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.css'],
    providers: [ EventsService, LoginService ]
})
export class CalendarComponent implements OnInit {
    public events: any;
    public header: any;
    public translate: any;
    public event: MyEvent;
    public dialogVisible: boolean;
    public idGen: number;
    public resData: any;
    constructor(public eventService: EventsService, private http: Http, public login: LoginService) { }
    public ngOnInit() {
        this.eventService.getEventData().subscribe(events => { this.events = events; });
        this.dialogVisible = false;
        this.idGen = 100;
        this.header = {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        };
        this.translate = CalendarConstants.ukr;
    }
    public handleDayClick(event) {
        this.event = new MyEvent();
        this.event.start = event.date.format();
        this.dialogVisible = true;
    }
    public handleEventClick(e) {
        this.event = new MyEvent();
        this.event.title = e.calEvent.title;
        const start = e.calEvent.start;
        const end = e.calEvent.end;
        if (e.view.name === 'month') {
            start.stripTime();
        }
        if (end) {
            end.stripTime();
            this.event.end = end.format();
        }
        this.event.id = e.calEvent.id;
        this.event.start = start.format();
        this.event.allDay = e.calEvent.allDay;
        this.dialogVisible = true;
    }
    public saveEvent() {
        if (this.event.id) {
            const index: number = this.findEventIndexById(this.event.id);
            if (index >= 0) {
                this.events[index] = this.event;
            }
        } else {
            this.event.id = this.idGen;
            this.events.push(this.event);
            this.event = null;
        }
        this.dialogVisible = false;
    }
    public deleteEvent() {
        const index: number = this.findEventIndexById(this.event.id);
        if (index >= 0) {
            this.events.splice(index, 1);
        }
        this.dialogVisible = false;
    }
    public findEventIndexById(id: number) {
        let index = -1;
        for (let i of this.events) {
            if (id === this.events[i].id) {
                index = i;
                break;
            }
        }
        return index;
    }
}
export class MyEvent {
    public id: number;
    public title: string;
    public start: string;
    public end: string;
    public allDay: boolean = true;
}
