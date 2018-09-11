import {Component} from '@angular/core';
import {LoginService} from "./login.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  providers: [LoginService]
})
export class LoginComponent {
  login = '';
  pass = '';
  constructor(private lService: LoginService) { }

  setLoginValue(event: any) {
    this.login = event.target.value;
  }

  setPassValue(event: any) {
    this.pass = event.target.value;
  }

  logIn() {
    this.lService.setUserRole(this.login, this.pass);
    //console.log(this.login + '  ' + this.pass);
  }
}
