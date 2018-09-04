import {NgModule} from "@angular/core";
import {HttpModule, JsonpModule} from "@angular/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserModule} from "@angular/platform-browser";
import {RootComponent} from "./root.component";
import {LoginComponent} from "./input/login.component";
import {MainFormComponent} from "./main_form/main_form.component";
import {UserComponent} from "./user.component";
import {HttpService} from "./HttpService";
import {ClientComponent} from "./client/client.component";
import {SearchPipe} from "./client/search.pipe";
import {HelloWorldVuasyaComponent} from "./hello_world_vuasya/hello-world-vuasya.component";
import {ModalComponent} from "./modal/modal.component";
import {CustomComponent} from "./custom/custom.component";
import {DebugComponent} from "./debug/debug.component";






@NgModule({
  imports: [
    BrowserModule, HttpModule, JsonpModule, FormsModule, ReactiveFormsModule
  ],
  declarations: [
    RootComponent, LoginComponent, MainFormComponent, UserComponent , ClientComponent,SearchPipe, HelloWorldVuasyaComponent,ModalComponent, CustomComponent, DebugComponent
  ],
  bootstrap: [RootComponent],
  providers: [HttpService],
  entryComponents: [],
})
export class AppModule {
}