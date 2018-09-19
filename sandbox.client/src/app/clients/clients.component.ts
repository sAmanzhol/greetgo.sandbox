import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {ClientsService} from "./clients.service";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  @Output() modal = new EventEmitter();
  data = {};
  target = "";
  type = "";
  query = "";

  constructor(public Service: ClientsService) { }

  ngOnInit() {
    this.Service.load("default", "asc", "");
  }

  openModal(type, clientId="") {
    this.data = {
      "type": type,
      "clientId": clientId
    };

    this.modal.emit(this.data);
  }

  sort(target, type) {
    this.target = target;
    this.type = type;
    this.Service.load(this.target, this.type, this.query);
  }

  search(query) {
    this.query = query;
    this.Service.load(this.target, this.type, this.query);
  }
}
