import { Injectable } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { InMemoryTokenService } from './in-memory-token.service';
import { environment as env } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class SocketService {
  constructor(private inMemoryTokenService: InMemoryTokenService) {}

  private stompClient: CompatClient;

  connect() {
    const socket = new SockJS(env.apiUrl + env.socketEndpoint);
    this.stompClient = Stomp.over(socket);

    let _this = this;
    this.stompClient.connect({}, function () {
      _this.stompClient.subscribe(env.tokenTopicPath, function (message) {
        let messageMap = JSON.parse(message.body);
        _this.inMemoryTokenService.createOrUpdate(messageMap[env.tokenKeyIdentifier], messageMap)
      });
    });
  }
}
