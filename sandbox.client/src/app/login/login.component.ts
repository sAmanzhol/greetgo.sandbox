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
  role = '';
  _admin: boolean = false;
  _user: boolean = false;
  constructor(private lService: LoginService) { }

  setLoginValue(event: any) {
    this.login = event.target.value;
  }

  setPassValue(event: any) {
    this.pass = event.target.value;
  }

  logIn() {
    this.role = this.lService.setUserRole(this.login, this.pass);
    if(this.role == 'Admin') {
      this._admin = true;
      this._user = false;
    }
    if(this.role == 'User') {
      this._user = true;
      this._admin = false;
    }
  }
}
