import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import {CompatClient, Stomp} from '@stomp/stompjs';
import { environment as env } from '../../environments/environment';


@Injectable({
  providedIn: 'root',
})
export class SocketService {

  private stompClient: CompatClient;

  constructor() {}

  connect() {
    const socket = new SockJS(env.apiUrl + env.socketEndpoint);
    this.stompClient = Stomp.over(socket);

    let _this = this
    this.stompClient.connect({}, function () {
      _this.stompClient.subscribe(env.tokenTopicPath, function (tokenMessage) {
        //TODO: update or create new token using by action header
      });
    });
  }
}
