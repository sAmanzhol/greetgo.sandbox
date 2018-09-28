import { Component, OnInit } from '@angular/core';
import {ClientRecord} from "../../model/ClientRecord";
import {TableModule} from 'primeng/table';
import {LazyLoadEvent, SortEvent} from "primeng/api";
import {LoginService} from "../login/login.service";
import {ClientTableService} from "./client-table.service";
import {forEach} from "@angular/router/src/utils/collection";

@Component({
  selector: 'app-client-table',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})
export class ClientTableComponent implements OnInit {
  cols : any[];
  clients : ClientItem[];
  totalRecords : number;

  ngOnInit() {
    this.clientTableService.load(null);

    this.transformClientRecToClientItem(this.clientTableService.list);

    this.totalRecords = 10;
    this.cols =
      [
      { field: 'fio', header: 'ФИО' },
      { field: 'charm', header: 'Характер' },
      { field: 'totalAccBal', header: 'общ.остаток счетов' },
      { field: 'maxAccBal', header: 'макс. остаток' },
      { field: 'minAccBal', header: 'мин. остаток' }
    ];
  }

  getTestData(){
    return [
      {fio:"Test1",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"},
      {fio:"Test2",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"},
      {fio:"Test3",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"},
      {fio:"Test4",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"},
      {fio:"Test6",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"},
      {fio:"Test5",age:10,charm:"Test",totalAccBal:"Test",maxAccBal:"Test",minAccBal:"Test"}
    ];
  }

  public transformClientRecToClientItem(clientRecs : ClientRecord[]) : ClientItem[]
  {
    let result : ClientItem[];

    for(let rec of clientRecs)
    {
      result.push({
        fio:rec.surname + " " + rec.name + " " + rec.patronomic,
        age:rec.getAge(),
        charm:rec.charm,
        totalAccBal:rec.getTotalAccBal(),
        maxAccBal:rec.getMaxAccBal(),
        minAccBal:rec.getMinAccBal()
      })
    }
    return result;
  }

  public loadData(event : LazyLoadEvent){
    console.log(event);
    this.clients = this.getTestData()///Нужно сделать запрос с параметрами

    //event.first = First row offset
    //event.rows = Number of rows per page
    //event.sortField = Field name to sort in single sort mode
    //event.sortOrder = Sort order as number, 1 for asc and -1 for dec in single sort mode
    //multiSortMeta: An array of SortMeta objects used in multiple columns sorting. Each SortMeta has field and order properties.
    //filters: Filters object having field as key and filter value, filter matchMode as value
    //globalFilter: Value of the global filter if available
    //this.cars = //do a request to a remote datasource using a service and return the cars that match the lazy load criteria

  }

  constructor(public login:LoginService,public clientTableService : ClientTableService) {}


}

export interface ClientItem {
  fio;
  charm;
  age
  totalAccBal;
  maxAccBal;
  minAccBal;
}


