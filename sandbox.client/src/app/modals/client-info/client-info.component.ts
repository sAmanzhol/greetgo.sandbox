import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-client-info',
  templateUrl: './client-info.component.html',
  styleUrls: ['./client-info.component.css']
})
export class ClientInfoComponent implements OnInit {
  @Input() data: {};
  characters = [];
  client = {};

  constructor() { }

  ngOnInit() {
    this.characters = [
      {
        "id": 1,
        "value": "Nice1"
      },
      {
        "id": 2,
        "value": "Nice2"
      },
      {
        "id": 3,
        "value": "Nice3"
      },
      {
        "id": 4,
        "value": "Nice4"
      },
      {
        "id": 5,
        "value": "Nice5"
      },
      {
        "id": 6,
        "value": "Nice6"
      },
      {
        "id": 7,
        "value": "Nice7"
      }
    ]
    this.client = {
      "id": '',
      "surname": '',
      "name": '',
      "patronymic": '',
      "birthDate": '',
      "gender": '',
      "character": '',
      "streetRegistration": '',
      "houseRegistration": '',
      "apartmentRegistration": '',
      "streetResidence": '',
      "houseResidence": '',
      "apartmentResidence": '',
      "phoneHome": '',
      "phoneWork": '',
      // "phoneExtra3": '',
      // "phoneExtra4": '',
      // "phoneExtra5": '',
    };
  }

  onSubmit() {
    console.log("kek");
  }
}
