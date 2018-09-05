import {Component, Input, NgModule, OnInit} from '@angular/core';
import {BrowserModule} from "@angular/platform-browser";

@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {

  @Input() buttonIsPressed = false;

  constructor() { }

  ngOnInit() {
  }

}

