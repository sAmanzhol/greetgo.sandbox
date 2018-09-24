import {Component, OnInit} from '@angular/core';
import {ClientsService} from "./clients.service";
import {ClientToFilter} from "../../model/ClientToFilter";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {
  modalData = {};
  filter = ClientToFilter.createDefault();

  constructor(public Service: ClientsService) {
  }

  ngOnInit() {
    this.modalData = {
      "open": false,
      "type": "",
      "clientId": null
    };

    this.loadPage();
  }

  loadPage() {
    this.Service.load(this.filter);
  }

  openModal(type, clientId) {
    this.modalData = {
      "open": true,
      "type": type,
      "clientId": clientId
    };
  }


  closeModal(data) {
    this.modalData = data;
  }

  sort(target, type) {
    this.filter.target = target;
    this.filter.type = type;

    this.loadPage();
  }

  search(query) {
    this.filter.query = query;

    this.loadPage();
  }

  delete(id, filter = this.filter) {
    this.Service.delete(id, filter);
  }
}
