import {Component, Inject, Input, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material";
import {ClientDetailService} from "./client-detail.service";
import {ClientToSave} from "../../model/ClientToSave";
import {ClientDetail} from "../../model/ClientDetail";


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],

})


export class ClientDetailComponent implements OnInit {

  public clientToSave: ClientToSave = new ClientToSave();
  clientDetail: ClientDetail = new ClientDetail;
  birthday: Date;

  constructor(public clientDetailService: ClientDetailService, @Inject(MAT_DIALOG_DATA) public data: number) {
  }

  ngOnInit() {
    this.init();
  }

  async init() {
    this.clientDetailService.load(this.data);
    this.clientDetail = await this.clientDetailService.loadClientRecord(this.data);
    this.clientToSave = ClientToSave.create(this.clientDetail, this.data);
    this.birthday = new Date(this.clientToSave.birthDay);
  }

  save() {

  }
}


