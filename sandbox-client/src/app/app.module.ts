import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MainWindowComponent } from './main-window/main-window.component';
import { LoginFormComponent } from './login-form/login-form.component';

@NgModule({
  declarations: [
    MainWindowComponent,
    LoginFormComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [MainWindowComponent]
})
export class AppModule {}
