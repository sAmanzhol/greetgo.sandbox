import {Component, OnInit, ViewChild} from "@angular/core";
import {ClientTableService} from "./client-table.service";
import {MatDialog, MatPaginator, PageEvent} from "@angular/material";
import {ClientDetailComponent} from "../client-detail/client-detail.component";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";

@Component({
  selector: 'app-client-table',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})

export class ClientTableComponent implements OnInit {

  public clientRecordList: ClientRecord[] = [];
  displayedColumns: string[] = ['fio', 'character', 'age', 'totalBalance', 'maxBalance', 'minBalance', 'actions'];

  length = 100;
  pageSize = 5;
  pageSizeOptions: number[] = [1, 2, 5, 10, 25, 100];
  pageInd = 0;
  prevSortBy: string;

  public clientFilter: ClientFilter = new ClientFilter("", "", "", this.pageInd, this.pageSize, "", false);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  pageEvent: PageEvent = new PageEvent();

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

  async pagination(a) {
     // console.log(a);
    if (a) {
      this.clientFilter.offset = await a.pageIndex;
      this.clientFilter.limit = await a.pageSize;
      // console.log("limit: ", this.clientFilter.limit);
      // console.log("offset: ", this.clientFilter.offset);
    }
    this.filtering();
  }

  sorting(sortBy: string) {
    console.log("sortBy: ", sortBy);
    this.clientFilter.columnName = sortBy;
    if (this.clientFilter.isAsc) {
      this.clientFilter.isAsc = false;
    } else {
      this.clientFilter.isAsc = true;
    }
    if (this.prevSortBy != sortBy) {
      this.prevSortBy = sortBy;
      this.clientFilter.isAsc = true;
    }
    console.log("isAsc: ", this.clientFilter.isAsc);
    console.log("columnName: ", this.clientFilter.columnName);
    this.filtering();

  }

  constructor(public clientTableService: ClientTableService, public dialog: MatDialog) {
  }

  openDialog(id: number) {
    let dialogRef = this.dialog.open(ClientDetailComponent, {
      data: id,
    });
    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed = ' + result);
      if (result) {
        this.filtering();
      }
    });
  }

  async delete(rec: ClientRecord) {
    await this.clientTableService.deleteClient(rec);
    await this.filtering();
  }

  async filtering() {
    await this.clientTableService.filter(this.clientFilter);
    this.length = this.clientTableService.clientWrapper.count;
  }

  async clear() {
    this.clientFilter = new ClientFilter("", "", "", this.clientFilter.offset, this.clientFilter.limit, "", false);
    this.filtering();
  }

  ngOnInit() {
    this.init();
  }


  init() {
    this.filtering();
  }


}
