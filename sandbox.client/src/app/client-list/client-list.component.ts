import {Component, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";

export interface Client {
  FIO: string;
  character: string;
  age: number;
  totalBalance: number;
  maxBalance: number;
  minBalance: number;
}

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {
  clients: Client[];
  display:boolean = false;

  constructor(private _service: ClientService) {
  }

  ngOnInit() {
    this.getClients();
  }

  getClients(): void {
    this._service.getClients().subscribe((content) => {
      this.clients = content;
    });
  }
}
