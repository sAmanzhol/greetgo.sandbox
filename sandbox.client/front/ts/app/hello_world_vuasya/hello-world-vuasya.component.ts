import {Component, EventEmitter, OnInit, Output} from "@angular/core";
import {HttpService} from "../HttpService";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";

@Component({
  selector:'app-hello-wolrd-vuasya',
  template: require('./hello-world-vuasya.component.html'),
  styles: [require('./hello-world-vuasya.component.css')],

})
export class HelloWorldVuasyaComponent implements OnInit{
  registration: boolean = false;
  enterButtonEnabled: boolean = false;

  errorMessage: string | null = null;

  disabled: boolean = false;

  fieldEnterLogin: string = '';
  fieldEnterPassword: string = '';

  @Output() finish = new EventEmitter<void>();

  ngOnInit(){}
  constructor(private httpService: HttpService) {}


  enterButtonClicked() {
    this.disabled = true;
    this.enterButtonEnabled = false;

    this.httpService.post("/hello/greeting", {
      accountName: this.fieldEnterLogin,
    }).toPromise().then(res => {
      this.disabled = false;
      this.httpService.token = res.text();
      console.log('lololol')
      this.errorMessage = null;
      if (this.fieldEnterLogin == 'root') {
        localStorage.removeItem("lastGoodLogin");
      } else {
        localStorage.setItem("lastGoodLogin", this.fieldEnterLogin);
      }
      this.finish.emit();
    }, error => {
      this.disabled = false;
      this.enterButtonEnabled = true;
      console.error("AUTHENTICATION_UNKNOWN_ERROR", error);
      if (400 <= error.status && error.status < 500) {
        this.errorMessage = error.text();
      } else {
        this.errorMessage = error;
      }
    });
  }


}
