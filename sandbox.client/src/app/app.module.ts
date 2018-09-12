import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {LoginComponent} from './login/login.component';
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {ClientListComponent} from './client-list/client-list.component';

import {AppRoutingModule} from './app-routing.module';

import {ClientService} from "./service/client.service";
import {DataTableModule, DialogModule} from "primeng/primeng";
import {TableModule} from "primeng/table";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    DataTableModule,
    TableModule,
    DialogModule
  ],
  providers: [HttpService, ClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
