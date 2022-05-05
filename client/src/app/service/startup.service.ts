import {
  HttpClient,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import * as http from '@angular/common/http';
import * as core from '@angular/core';
import { InMemoryTokenService } from './in-memory-token.service';
import { environment as env } from '../../environments/environment';
import {
  catchError,
  of,
  delayWhen,
  throwError,
  retryWhen,
  interval,
} from 'rxjs';
import { SocketService } from './socket.service';

@core.Injectable({
  providedIn: 'root',
})
export class StartupService {
  constructor(
    private inMemoryTokenService: InMemoryTokenService,
    private socketService: SocketService,
    private http: HttpClient
  ) {}

  private setupInMemoryMap() {
    this.http
      .get<any[]>(env.apiUrl + env.tokenAPIPath, {
        observe: 'response',
      })
      .pipe(
        retryWhen((err) =>
          err.pipe(
            catchError((err) => {
              if (err.status === 503) {
                return of(err);
              }
              return throwError(() => new Error('Fail to get tokens.'));
            }),
            delayWhen(() => interval(Math.random() * 3000))
          )
        )
      )
      .subscribe((resp) => {
        this.inMemoryTokenService.set(resp.body)
      });
  }

  init() {
    this.setupInMemoryMap();
    this.socketService.connect()
  }
}
