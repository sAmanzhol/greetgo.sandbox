import {Component, Input, OnInit} from "@angular/core";
import {ClientTableService} from "./client-table.service";
import {MatDialog} from "@angular/material";
import {ClientDetailComponent} from "../client-detail/client-detail.component";
import {EditComponent} from "../edit/edit.component";
import {ClientRecord} from "../../model/ClientRecord";

@Component({
  selector: 'app-test',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})

export class TestComponent implements OnInit {


  constructor(public clientTableService: ClientTableService, public dialog: MatDialog) {
  }

  openDialog(id: number) {
    let dialogRef = this.dialog.open(ClientDetailComponent, {
      data: id,
    });
    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed = ' + result);
      //debugger;
      this.clientTableService.addClientToList(result);
    });
  }

  delete(id: number) {
    this.clientTableService.deleteClient(id);
  }

  ngOnInit() {
    this.clientTableService.load();
  }

}
