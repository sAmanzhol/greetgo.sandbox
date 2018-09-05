import{Component, Input, NgModule, OnInit} from '@angular/core';
import {ClientTableService} from "./client-table.service";


@Component({
  selector: 'app-test',
  templateUrl: './client-table.component.html',
  styleUrls: ['./client-table.component.css']
})
export class TestComponent implements OnInit {


  @Input() buttonIsPressed = false;

  constructor(public clientTableService: ClientTableService) { }

  ngOnInit() {

    this.clientTableService.load();
  }

}
