import {Component} from '@angular/core';
import {LoginService} from "./login/login.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
///MODIFY replace sandbox {PROJECT_NAME}
  title = 'sandbox';

  constructor(public login: LoginService) {}
}
