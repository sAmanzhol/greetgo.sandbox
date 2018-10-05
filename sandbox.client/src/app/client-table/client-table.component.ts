import {Component, OnInit, ViewChild,ChangeDetectorRef} from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {TableModule} from 'primeng/table';
import {LazyLoadEvent, SortEvent} from "primeng/api";
import {LoginService} from "../login/login.service";
import {ClientTableService} from "./client-table.service";
import {forEach} from "@angular/router/src/utils/collection";
import {ClientRepositoryService} from "../client-repository/client-repository.service";
import {HttpService} from "../http.service";
import {ClientTableInputComponent} from "../client-table-dialogs/client-table-input.component";
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Component({
  selector: 'app-client-table',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})

export class ClientTableComponent implements OnInit {
  cols: any[];
  clients: ClientItem[];
  totalRecords: number;

  @ViewChild(ClientTableInputComponent ) editDialog: ClientTableInputComponent ;


  constructor(public login: LoginService, public clientRepoService: ClientRepositoryService,private cdr: ChangeDetectorRef) {}

  ngOnInit() {
    this.totalRecords = this.clientRepoService.totalRecords;
    this.cols =
      [
        {field: 'fio', header: 'ФИО'},
        {field: 'charm', header: 'Характер'},
        {field: 'totalAccBal', header: 'общ.остаток счетов'},
        {field: 'maxAccBal', header: 'макс. остаток'},
        {field: 'minAccBal', header: 'мин. остаток'}
      ];
  }

  ngAfterViewChecked(){
    this.cdr.detectChanges();
  }

  delete(id: number) {
    this.editDialog.delete(id);
  }

  edit(id: number) {
    this.editDialog.invoke(id);
  }

  add(){
    this.editDialog.invoke(null);
  }

  public onRowSelect(event: any) {

  }

  public async loadData(event: LazyLoadEvent) {
    await this.clientRepoService.getRecordsCount();
    this.totalRecords = this.clientRepoService.totalRecords;
    console.log(this.totalRecords);
    this.clients = this.clientRepoService.getClientItem(event.rows,event.sortField,event.sortOrder);
   // this.clients = this.getTestData()///Нужно сделать запрос с параметрами

    //event.first = First row offset
    //event.rows = Number of rows per page
    //event.sortField = Field name to sort in single sort mode
    //event.sortOrder = Sort order as number, 1 for asc and -1 for dec in single sort mode
    //multiSortMeta: An array of SortMeta objects used in multiple columns sorting. Each SortMeta has field and order properties.
    //filters: Filters object having field as key and filter value, filter matchMode as value
    //globalFilter: Value of the global filter if available
    //this.cars = //do a request to a remote datasource using a service and return the cars that match the lazy load criteria
  }

  }

export interface ClientItem {
  id;
  fio;
  charm;
  age
  totalAccBal;
  maxAccBal;
  minAccBal;
}



