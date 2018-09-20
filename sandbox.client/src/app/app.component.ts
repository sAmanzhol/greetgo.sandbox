import {Component} from '@angular/core';
import {LoginService} from "./login/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  public isModalOpened = false;
  title = 'sandbox';
  data = {};

  constructor(public login: LoginService) {}

  openModal(data) {
    console.log(data["open"]);

    if (data["open"]) {
      this.data = data;
      console.log(data["type"], data["clientId"]);
      this.isModalOpened = true;

      console.log("Opened")
    } else if (data["open"] === false) {
      this.isModalOpened = false;
      console.log("Closed")
    }
  }
}
