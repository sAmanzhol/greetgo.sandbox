import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ClientDetailService} from "./client-detail.service";
import {ClientToSave} from "../../model/ClientToSave";
import {ClientDetail} from "../../model/ClientDetail";
import {Phone} from "../../model/Phone";
import {PhoneDetail} from "../../model/PhoneDetail";


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],

})


export class ClientDetailComponent implements OnInit {

  public clientToSave: ClientToSave = new ClientToSave();
  clientDetail: ClientDetail = new ClientDetail();
  public phone: Phone = new Phone();
  date: Date;

  constructor(public clientDetailService: ClientDetailService, public dialogRef: MatDialogRef<ClientDetailComponent>, @Inject(MAT_DIALOG_DATA) public data: number) {
  }

  ngOnInit() {
    this.init();
  }

  async init() {
    this.clientDetailService.load(this.data);
    this.clientDetail = await this.clientDetailService.loadClientDetailRecord(this.data);
    this.clientToSave = ClientToSave.create(this.clientDetail);
    console.log("BirthDay:" + this.clientDetail.birthDay);

    this.date = new Date("2015-03-25");
    // this.date.setDate(this.date.getDate() + 3);
    console.log("Date is: " + this.date);
  }

  async saveClient() {
    if (this.phone.number != "" || this.phone.detail != null) {
      await this.savePhone();
    }
    let a = await this.clientDetailService.saveClient(this.clientToSave);
    this.dialogRef.close(a);
  }

  public savePhone() {
    this.clientToSave.phones.push({
      detail: {type: this.phone.detail.type, typeRuss: ""},
      number: this.phone.number,
      oldNumber: this.phone.oldNumber
    });//this.phone
    this.phone.number = "";
    this.phone.detail = new PhoneDetail();
  }

}


