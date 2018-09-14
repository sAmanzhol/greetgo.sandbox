import {Component, OnInit, ViewChild} from "@angular/core";
import {ClientTableService} from "./client-table.service";
import {MatDialog, MatPaginator, MatTableDataSource, PageEvent} from "@angular/material";
import {ClientDetailComponent} from "../client-detail/client-detail.component";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";

@Component({
  selector: 'app-test',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})

export class ClientTableComponent implements OnInit {

  public clientRecordList: ClientRecord[] = [];
  displayedColumns: string[] = ['fio', 'character', 'age', 'totalBalance', 'maxBalance', 'minBalance', 'actions'];
  dataSource: any;// =  new MatTableDataSource<ClientRecord>(this.clientRecordList);

  length = 100;
  pageSize = 10;
  pageSizeOptions: number[] = [1, 2, 5, 10, 25, 100];

  public clientFilter: ClientFilter = new ClientFilter("","","");

  @ViewChild(MatPaginator) paginator: MatPaginator;

  pageEvent: PageEvent;

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }


  test(a) {
    console.log(a);
  }

  constructor(public clientTableService: ClientTableService, public dialog: MatDialog) {
  }

  openDialog(id: number) {
    let dialogRef = this.dialog.open(ClientDetailComponent, {
      data: id,
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed = ' + result);
      //debugger;
      if (result) {
        this.clientTableService.addClientToList(result);
      }
    });
  }

  async delete(rec: ClientRecord) {
    await this.clientTableService.deleteClient(rec);
    //this.dataSource =  new MatTableDataSource<ClientRecord>(this.clientRecordList);
  }

  filtering() {
   this.clientTableService.filter(this.clientFilter);
    // this.clientTableService.
  }

  ngOnInit() {
    this.init();
    this.clientTableService.load();
  }

  async init() {

    this.clientTableService.load();
    this.clientRecordList = await this.clientTableService.loadRecords();
    this.dataSource = await new MatTableDataSource<ClientRecord>(this.clientRecordList);
    this.dataSource.paginator = this.paginator;
    //this. = ClientToSave.create(this.clientDetail);
  }

}
