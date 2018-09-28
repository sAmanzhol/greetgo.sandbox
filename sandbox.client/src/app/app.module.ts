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
import {AboutComponent } from './about/about.component';
import {ClientTableComponent } from './client-table/client-table.component';
import {SharedModule} from 'primeng/primeng'
import {TableModule} from 'primeng/table';
import {ConfirmDialogModule} from 'primeng/confirmdialog';
import {ConfirmationService} from 'primeng/api';
import {MessagesModule} from 'primeng/messages'
import {ClientTableInputComponent} from './client-table/client-table-input.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {InputTextModule} from 'primeng/inputtext';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    ClientTableComponent,
    ClientTableInputComponent,

  ],
  imports: [
    BrowserAnimationsModule,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    TableModule,
    SharedModule,
    ConfirmDialogModule,
    ButtonModule,
    MessagesModule,
    DialogModule,
    InputTextModule
  ],

  providers: [HttpService, LoginService, ClientListService,ConfirmationService],
  bootstrap: [AppComponent]
})
export class AppModule {}
