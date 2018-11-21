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

    this.http.token = await this.http.post("/auth/login", {
      username: this.username,
      password: this.password,
    }, "text").toPromise().then(resp => resp.body as string);

    console.log("this.http.token = " + this.http.token)
  }
}
