import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MainWindowComponent } from './main-window/main-window.component';

@NgModule({
  declarations: [
    MainWindowComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [MainWindowComponent]
})
export class AppModule {}
