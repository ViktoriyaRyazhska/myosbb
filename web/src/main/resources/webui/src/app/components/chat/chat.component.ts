import { Component, OnInit } from '@angular/core';
import { StompService } from './stomp.service';
import { WebSocket_URL } from '../../../shared/models/localhost.config';
import { Http, Response } from '@angular/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { API_URL } from '../../../shared/models/localhost.config';
import { UsersService } from '../users/users.service';
import { User } from '../../models/user.model';
import { LoginService } from '../../shared/login/login.service';

@Component({
    selector: 'chat',
    templateUrl: 'chat.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './chat.component.css'],
    providers: [StompService, UsersService]
})

export class ChatComponent implements OnInit {
  public title: string = `Chat`;
  public inputField = '';
  public serverResponse=[];
  public messages: string[];
  public currentUser: User;
  constructor(public route: ActivatedRoute, public http: Http, private _stompService: StompService, public usersService: UsersService, private currentUserService: LoginService) {
  }

  public ngOnInit(): void {
   this._stompService.getMessages().subscribe((data) => {
                this.messages = data;
            });
   this._stompService.connect(`${WebSocket_URL}/stompTest`);
   this.serverResponse= this._stompService._serverResponse;
   this.getCurrentUser();
  }

  public send(): void {
    this._stompService.send(this.inputField, this.currentUserService.getUser());
    this.inputField='';
  }

  public getCurrentUser() {
        this.currentUser = this.currentUserService.getUser();
  }

}