import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";

export interface ClientRecord {
  lastName: string,
  name: string,
  fatherName: string;
  character: string;
  age: number;
  totalBalance: number;
  maxBalance: number;
  minBalance: number;
}

export interface ClientDetail {
  id: number;
  lastName: string,
  name: string,
  fatherName: string;
  gender: string;
  birthDate: string;
  character: string;
  factStreet: string;
  factNo: string;
  factFlat: string;
  regStreet: string;
  regNo: string;
  regFlat: string;
  homePhoneNumber: string;
  workPhoneNumber: string;
  mobileNumber1: string;
  mobileNumber2: string;
  mobileNumber3: string;
}

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: ClientRecord[];
  @Input() clientDetail: ClientDetail;
  display: boolean = false;
  selectedClient: ClientRecord;
  gender: string = 'Мужской';
  birthDate: Date;
  selectedCharacter: string;
  characters;
  date: Date;


  constructor(private _service: ClientService) {
    this.characters = [
      {value: 'спокойный'},
      {value: 'активный'},
      {value: 'аккуратный'},
      {value: 'артистичный'},
      {value: 'бдительный'},
      {value: 'безобидный'},
      {value: 'веселый'},
      {value: 'грозный'}
    ];
  }

  ngOnInit() {
    this.getClientRecords();
  }

  getClientRecords(): void {
    this._service.getClientRecords().subscribe((content) => {
      this.clients = content;
    });
  }

  onSelect(c: ClientRecord) {
    console.log(c);
    this.selectedClient = c;
  }

  edit(id: number) {
    this._service.getClientDetail(id).subscribe((content) => {
      this.clientDetail = content;
      console.log(content)
    });
    this.display = true;
  }

  add() {
    this.display = true;
  }

  cancel() {
    // this.display = false;
    this._service.updateClient(this.clientDetail)
      .subscribe();
  }

  saveClient() {
    console.log("clicked");
    this._service.updateClient(this.clientDetail)
      .subscribe(() => this.cancel());
  }

}
