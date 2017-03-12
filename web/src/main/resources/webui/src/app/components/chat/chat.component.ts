import { Component, OnInit } from '@angular/core';
import { StompService } from './stomp.service';
import { API_URL } from '../../../shared/models/localhost.config';

@Component({
    selector: 'chat',
    templateUrl: 'chat.component.html',
    styleUrls: ['../../../assets/css/manager.page.layout.scss', './chat.component.css'],
    providers: [StompService]
})

export class ChatComponent implements OnInit {
  public title: string = `Chat`;
  public inputField = '';
  public serverResponse=[];
  constructor(private _stompService: StompService) {
  }

  public ngOnInit(): void {
   this._stompService.connect('ws://localhost:8080/myosbb/stompTest');
   this.serverResponse= this._stompService._serverResponse;
  }

  public send(): void {
    this._stompService.send(this.inputField);
    this.inputField='';
  }

}