import {ChangeDetectorRef, Component, OnInit, ViewChild} from "@angular/core";
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
  // dataSource: any;// =  new MatTableDataSource<ClientRecord>(this.clientRecordList);

  length = 100;
  pageSize = 5;
  pageSizeOptions: number[] = [1, 2, 5, 10, 25, 100];
  pageInd = 0;

  public clientFilter: ClientFilter = new ClientFilter("","","", this.pageInd, this.pageSize);

  @ViewChild(MatPaginator) paginator: MatPaginator;

  pageEvent: PageEvent = new PageEvent();

  setPageSizeOptions(setPageSizeOptionsInput: string) {
    this.pageSizeOptions = setPageSizeOptionsInput.split(',').map(str => +str);
  }

//Fixme переименовать test
  async test(a) {
    console.log(a);
    if(a) {
      // this.pageInd = await a.pageIndex;
      // this.length = await this.clientRecordList.length;//Math.ceil(this.clientRecordList.length / this.pageSize);
      // this.pageSize = await a.pageSize;
      //
      this.clientFilter.offset = await a.pageIndex;
      this.clientFilter.limit = await a.pageSize;
      console.log("limit: ", this.pageSize);
      console.log("offset: ", this.pageInd);
    }
    this.filtering();
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
        //this.clientTableService.addClientToList(result);
        this.clientTableService.filter(this.clientFilter);
      }
    });
  }

  async delete(rec: ClientRecord) {
    await this.clientTableService.deleteClient(rec);
    this.clientTableService.filter(this.clientFilter);
    //this.list = this.filtering();
  }

  filtering() {
    //FIXME Добавить offset and pageSize in Filter object
   this.clientTableService.filter(this.clientFilter);
    // this.clientTableService.
  }

  ngOnInit() {
    this.init();
    //this.clientTableService.load(this.clientFilter);
  }

  async init() {
    this.clientTableService.load(this.clientFilter);
    //this.clientRecordList = await this.clientTableService.loadRecords();
    // this.dataSource = await new MatTableDataSource<ClientRecord>(this.clientRecordList);
    // this.dataSource.paginator = this.paginator;
    this.clientTableService.filter(this.clientFilter);
  }

  test1(){
    console.log(this.clientTableService.list);
  }


}
