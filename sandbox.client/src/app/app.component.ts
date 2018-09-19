import {Component} from '@angular/core';
import {LoginService} from "./login/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'sandbox';
  data = {};
  isModalOpened = false;

  constructor(public login: LoginService) {}

  openModal(data) {
    this.data = data;
    console.log(data["type"], data["clientId"]);
    this.isModalOpened = true;
  }
}
