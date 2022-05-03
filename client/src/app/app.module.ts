import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MaterialModule } from './material/material.module';
import { NotFoundPageComponent } from './not-found-page/not-found-page.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TokenPageComponent } from './token-page/token-page.component';
import { inherits } from 'util';
import { StartupServiceService } from './service/startup-service.service';

@NgModule({
  declarations: [AppComponent, NotFoundPageComponent, TokenPageComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MaterialModule,
    BrowserAnimationsModule,
  ],
  providers: [
    StartupServiceService,
    {
      provide: APP_INITIALIZER,
      useFactory: initFunction,
      deps: [StartupServiceService],
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}

export function initFunction(startupService: StartupServiceService) {
  return () => startupService.init();
}
