import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {LoginService} from "./login/login.service";
import {FormsModule} from "@angular/forms";
import {ClientListComponent} from './client-list/client-list.component';
import {ClientListService} from "./client-list/client-list.service";
import { AboutComponent } from './about/about.component';
import { TestComponent } from './test/test.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    TestComponent,
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule,
  ],
  providers: [HttpService, LoginService, ClientListService],
  bootstrap: [AppComponent]
})
export class AppModule {}
