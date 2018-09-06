import {BrowserModule} from "@angular/platform-browser";
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from "@angular/core";
import {AppComponent} from "./app.component";
import {LoginComponent} from "./login/login.component";
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {LoginService} from "./login/login.service";
import {FormsModule} from "@angular/forms";
import {ClientListComponent} from "./client-list/client-list.component";
import {ClientListService} from "./client-list/client-list.service";
import {AboutComponent} from "./about/about.component";
import {TestComponent} from "./client-table/client-table.component";
import {ClientDetailComponent} from "./client-detail/client-detail.component";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {
  MatButtonModule, MatDialogModule, MatIconModule, MatInputModule, MatSelectModule,
  MatTableModule
} from "@angular/material";

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientListComponent,
    AboutComponent,
    TestComponent,
    ClientDetailComponent,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  // exports: [MatDialogModule],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatIconModule,
    MatButtonModule,
    MatTableModule,
    MatInputModule,
    MatSelectModule,
  ],
  entryComponents: [ClientDetailComponent],
  providers: [HttpService, LoginService, ClientListService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
