import {Component, OnInit, ViewChild} from '@angular/core';
import {LazyLoadEvent} from "primeng/api";
import {LoginService} from "../login/login.service";
import {ClientRepositoryService} from "../client-repository/client-repository.service";
import {ClientTableInputFormsComponent} from "../client-table-inputForms/client-table-input-forms.component";
import {ClientReqParams} from "../../model/ClientReqParams";
import {ClientDisplay} from "../../model/ClientDisplay";

@Component({
  selector: 'app-client-table',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})

export class ClientTableComponent implements OnInit {
  private DEFAULT_TABLE_SIZE = 10;

  cols: any[];
  clients: ClientDisplay[];
  nameFilterVal : string = "";
  surnameFilterVal : string = "";
  patronymicFilterVal : string = "";
  totalRecords: number;
  tableSize:number;
  lastParams:ClientReqParams;

  @ViewChild(ClientTableInputFormsComponent) editDialog: ClientTableInputFormsComponent;

  constructor(public login: LoginService, public clientRepoService: ClientRepositoryService) {}

  ngOnInit() {
    this.cols =
      [
        {field: 'fio', header: 'ФИО'},
        {field: 'charm_name', header: 'Характер'},
        {field: 'age', header: 'Возраст'},
        {field: 'sumbal', header: 'общ.остаток счетов'},
        {field: 'maxbal', header: 'макс. остаток'},
        {field: 'minbal', header: 'мин. остаток'}
      ];
    this.tableSize = this.DEFAULT_TABLE_SIZE;
  }

  delete(id: number) {
    this.editDialog.delete(id);
  }

  edit(id: number) {
    this.editDialog.invoke(id);
  }

  add() {
    this.tableSize++;
    this.editDialog.invoke(null);
  }

  public async loadClientData(event: LazyLoadEvent) {
    if(event == null && event == undefined) {
      this.lastParams.nameFilterVal = this.nameFilterVal;
      this.lastParams.surnameFilterVal = this.surnameFilterVal;
      this.lastParams.patronymicFilterVal = this.patronymicFilterVal;
    }
    else {
      this.lastParams = new ClientReqParams
      (
        this.nameFilterVal,
        this.surnameFilterVal,
        this.patronymicFilterVal,
        event.first,
        event.rows,
        event.sortField == "fio" ? "surname" : event.sortField,
        event.sortOrder == -1 ? 0 : 1
      );
    }
    this.totalRecords = await this.clientRepoService.loadClientsCount().then(body => {return body});
    this.clients = await this.clientRepoService.getDispClientList(this.lastParams);
  }

  getXlsx() {
    this.clientRepoService.getXlsx();
  }
}




