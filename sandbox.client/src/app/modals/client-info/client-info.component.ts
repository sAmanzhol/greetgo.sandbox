import {Component, ElementRef, EventEmitter, Input, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {ClientDisplay} from "../../../model/ClientDisplay";
import {ClientInfoService} from "../../modals/client-info/client-info.service";
import {ClientsService} from "../../clients/clients.service";

@Component({
  selector: 'app-client-info',
  templateUrl: './client-info.component.html',
  styleUrls: ['./client-info.component.css']
})
export class ClientInfoComponent implements OnInit {
  @Input() data: {};
  @Output() modal = new EventEmitter();
  characters = [];
  client = new ClientDisplay();

  public loading: boolean = false;

  @ViewChild('closeButton') closeButton: ElementRef;

  constructor(public Service: ClientInfoService, public ClientsService: ClientsService) { }

  ngOnInit() {
    if (this.data["type"] == "edit") {
      this.getClient(this.data["clientId"]);
    }

    this.getCharacters();
  }

  ngOnChanges() {
    if (this.data["type"] == "edit") {
      this.getClient(this.data["clientId"]);
    } else {
      this.client = new ClientDisplay();
    }
  }

  onSubmit() {
    console.log(this.client);

    this.crupdate(this.client);
  }

  closeModal() {
    this.data = {
      "open": false,
      "type": "",
      "clientId": ""
    };

    this.closeButton.nativeElement.click();
    this.modal.emit(this.data);
    // Maybe will be needed to rewrite this realization!
  }

  async getClient(id) {
    try {
      this.loading = true;
      this.client = await this.Service.getClient(id);
      this.loading = false;

      return this.client
    } catch (e) {

      this.loading = false;
      console.error(e);
    }
  }

  async getCharacters() {
    try {
      this.loading = true;
      this.characters = await this.Service.getCharacters();
      this.loading = false;

      return this.client
    } catch (e) {

      this.loading = false;
      console.error(e);
    }
  }

  async crupdate(clientDisplay) {
    try {
      this.loading = true;
      this.client = await this.Service.crupdateClient(clientDisplay);
      this.loading = false;
      console.log(this.client);

      this.closeModal();
      this.ClientsService.load("default", "asc", "");
    } catch (e) {

      this.loading = false;
      console.error(e);
    }
  }
}
