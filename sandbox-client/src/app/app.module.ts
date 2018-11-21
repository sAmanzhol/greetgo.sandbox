import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {MainWindowComponent} from './main-window/main-window.component';
import {LoginFormComponent} from './login-form/login-form.component';
import {HttpService} from "./http.service";
import {LoginService} from "./login-form/login.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    MainWindowComponent,
    LoginFormComponent
  ],
  imports: [
    BrowserModule, HttpClientModule, FormsModule,
  ],
  providers: [HttpService, LoginService],
  bootstrap: [MainWindowComponent]
})
export class AppModule {}
