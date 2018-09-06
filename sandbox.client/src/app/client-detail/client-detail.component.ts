import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material";
import {ClientDetail} from "../../model/ClientDetail";
import {ClientDetailService} from "./client-detail.service";


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],

})


export class ClientDetailComponent implements OnInit {



  constructor(clientDetailService: ClientDetailService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    //this.clientDetailService.load(this.data);

  }

}

