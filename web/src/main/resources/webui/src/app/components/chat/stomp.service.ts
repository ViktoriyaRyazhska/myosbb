import { Injectable } from '@angular/core';
import {Subject} from 'rxjs/Subject';
import {Observable} from 'rxjs/Observable';

import 'stompjs';

declare let Stomp:any;
@Injectable()
export class StompService {

    private _stompClient; 
    public _serverResponse =[];
    public connect(_webSocketUrl: string) : void {
        let self = this;
        let webSocket = new WebSocket(_webSocketUrl);
        this._stompClient = Stomp.over(webSocket);
        this._stompClient.connect({}, function (frame) {
        self._stompClient.subscribe('/topic/greetings', function (stompResponse) {
        self._serverResponse.push(JSON.parse(stompResponse.body));
            });
        });
    }
    public send(_payload: string) {
        this._stompClient.send("/app/chat", {}, JSON.stringify({'message': _payload}));
    }

}
