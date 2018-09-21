import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {ClientInfoService} from "./client-info.service";
import {ClientsService} from "../../clients/clients.service";
import {ClientToFilter} from "../../../model/ClientToFilter";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ClientToSave} from "../../../model/ClientToSave";

@Component({
  selector: 'app-client-info',
  templateUrl: './client-info.component.html',
  styleUrls: ['./client-info.component.css']
})
export class ClientInfoComponent implements OnInit {
  @Input() inModalData: {};
  @Output() outModalData = new EventEmitter();

  closeResult: string;
  characters = [];
  client = new ClientToSave();
  filter = ClientToFilter.createDefault();

  @ViewChild('modal') modal: ElementRef;

  constructor(public Service: ClientInfoService, public ClientsService: ClientsService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getCharacters();
  }

  ngOnChanges() {
    if (this.inModalData["type"] == "edit") {
      this.getClient(this.inModalData["clientId"]);
    } else {
      this.client = new ClientToSave();
    }

    setTimeout(() => this.modalService.open(this.modal, {size: 'lg'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${reason}`;
    }));
  }

  onSubmit() {
    this.crupdate(this.client);
  }

  closeModal() {
    this.inModalData = {
      "open": false,
      "type": "",
      "clientId": ""
    };

    this.outModalData.emit(this.inModalData);
    this.modalService.dismissAll();
  }

  async getClient(id) {
    try {
      this.client = await this.Service.getClient(id);
    } catch (e) {
      console.error(e);
    }
  }

  async getCharacters() {
    try {
      this.characters = await this.Service.getCharacters();
    } catch (e) {
      console.error(e);
    }
  }

  async crupdate(clientToSave) {
    try {
      await this.Service.crupdateClient(clientToSave);

      this.closeModal();
      this.ClientsService.load(this.filter);
    } catch (e) {
      console.error(e);
    }
  }
}
