import {Component, OnInit} from '@angular/core';
import {LoginService} from "./login.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  constructor(public loginService: LoginService) {}

  ngOnInit() {}

}
