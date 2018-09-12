import {Component, OnInit} from '@angular/core';
import {HttpService} from "./http.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'sandbox_Aizhan';

  constructor(private http: HttpService) {

  }

  ngOnInit(): void {
    // this.http.get("/person/test").toPromise().then(res=>{
    //   console.log(res.body)
    // }).catch(err=>{
    //   alert(err)
    // })
  }
}
