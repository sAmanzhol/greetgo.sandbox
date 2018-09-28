import {Component, OnInit} from '@angular/core';
import {ClientsService} from "./clients.service";
import {ClientToFilter} from "../../model/ClientToFilter";
import {ClientRecord} from "../../model/ClientRecord";

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent implements OnInit {

  filter = ClientToFilter.createDefault();
  public list: ClientRecord[] = [];
  public count: number = 0;
  modalData = {
    "clientId": null
  };
  modalOpen = false;

  constructor(public Service: ClientsService) {
  }

  ngOnInit() {
    this.loadPage();
  }

  openModal(clientId) {
    this.modalOpen = true;
    this.modalData = {
      "clientId": clientId
    };
  }

  closeModal(data) {
    this.modalOpen = false;
    this.modalData = data;
  }

  sort(target, type) {
    this.filter.sortColumn = target;
    this.filter.sortDirection = type;

    this.loadPage();
  }

  async loadPage(filter = this.filter) {
    this.count = await this.Service.getCount(filter);
    this.list = await this.Service.getClients(filter);
  }

  async delete(id, filter = this.filter) {
    await this.Service.deleteClient(id);
    this.loadPage(filter);
  }
}
