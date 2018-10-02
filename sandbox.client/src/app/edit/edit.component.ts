import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from "@angular/material";
import {EditService} from "./edit.service";

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  counterValue = 5;
  constructor(public editService: EditService, @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
    this.editService.load(this.data);
  }
}
