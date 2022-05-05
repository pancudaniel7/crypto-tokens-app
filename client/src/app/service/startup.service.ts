import {
  HttpClient,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
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

@Injectable({
  providedIn: 'root',
})
export class StartupService {
  constructor(
    private inMemoryTokenService: InMemoryTokenService,
    private http: HttpClient
  ) {}

  private socketConnect() {
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
    this.socketConnect();
  }
}
