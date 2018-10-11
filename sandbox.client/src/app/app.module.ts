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
import {ClientTableInputFormsComponent} from './client-table-inputForms/client-table-input-forms.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ButtonModule} from 'primeng/button';
import {DialogModule} from 'primeng/dialog';
import {InputTextModule} from 'primeng/inputtext';
import {DropdownModule} from 'primeng/dropdown';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputMaskModule} from 'primeng/inputmask';
import { LOCALE_ID } from '@angular/core';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    ClientTableComponent,
    ClientTableInputFormsComponent,

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
    InputTextModule,
    DropdownModule,
    RadioButtonModule,
    InputMaskModule
  ],

providers: [HttpService, LoginService, ClientListService,ConfirmationService,
  {provide: LOCALE_ID, useValue: "ru-RU" },],
  bootstrap: [AppComponent]
})
export class AppModule {}
