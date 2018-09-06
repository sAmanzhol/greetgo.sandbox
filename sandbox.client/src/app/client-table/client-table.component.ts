import {Component, Input, OnInit} from "@angular/core";
import {ClientTableService} from "./client-table.service";
import {MatDialog} from "@angular/material";
import {ClientDetailComponent} from "../client-detail/client-detail.component";

@Component({
  selector: 'app-test',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})
export class TestComponent implements OnInit {


  @Input() buttonIsPressed = false;

  constructor(public clientTableService: ClientTableService, public dialog: MatDialog) {
  }

  print(message: string) {
    this.buttonIsPressed = true;
    console.log(message);
  }

  openDialog() {
    const dialogRef = this.dialog.open(ClientDetailComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }

  showModal() {
    this.buttonIsPressed = true;
  }

  ngOnInit() {
    this.clientTableService.load();
  }
}
