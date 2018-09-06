import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup} from '@angular/forms';


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css']
})
export class ClientDetailComponent implements OnInit {

  test() {
    console.log("Hi!!");
  }

  // options: FormGroup;
  //
  // constructor(fb: FormBuilder) {
  //   this.options = fb.group({
  //     hideRequired: false,
  //     floatLabel: 'auto',
  //   });
  // }

  constructor() {

  }

  ngOnInit() {
  }

}

