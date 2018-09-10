import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material";
import {ClientDetailService} from "./client-detail.service";


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],

})


export class ClientDetailComponent implements OnInit {

  counterValue = 5;
  nextDay = new Date(clientDetailService.clientDetail.birthDay);//"2017-05-10");

  myFilter = (d: Date): boolean => {
    const day = d.getDay();
    return day < 7;
  }

  constructor(public clientDetailService: ClientDetailService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.clientDetailService.load(this.data);
  }
}

