import {NgModule} from "@angular/core";
import {HttpModule, JsonpModule} from "@angular/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RootComponent} from "./root.component";
import {LoginComponent} from "./input/login.component";
import {MainFormComponent} from "./main_form/main_form.component";
import {HttpService} from "./HttpService";
import {TodayComponent} from "./today/today.component";
import {ClientListComponent} from "./client/client-list.component";




@NgModule({
  imports: [
    BrowserModule, HttpModule, JsonpModule, FormsModule, ReactiveFormsModule
  ],
  declarations: [
    RootComponent, LoginComponent, MainFormComponent , TodayComponent, ClientListComponent
  ],
  bootstrap: [RootComponent],
  providers: [HttpService],
  entryComponents: [],
})
export class AppModule {
}