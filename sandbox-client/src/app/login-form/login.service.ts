import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  public username: string = '';
  public password: string = '';

  constructor(private http: HttpService) { }

  async login() {
    console.log("login as username = " + this.username + ", password = " + this.password);
  }
}
