import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";
import {SelectItem} from "primeng/api";
import {Validators,FormControl,FormGroup,FormBuilder} from "@angular/forms";
import {MessageService} from 'primeng/api';

export class ClientRecord {
  id: number;
  lastName: string;
  name: string;
  fatherName: string;
  character: string;
  age: number;
  totalBalance: number;
  maxBalance: number;
  minBalance: number;
}

export class ClientDetail {
  id: number;
  lastName: string;
  name: string;
  fatherName: string;
  gender: string;
  birthDate: string;
  character: string;
  factStreet: string;
  factNo: string;
  factFlat: string;
  regStreet: string;
  regNo: string;
  regFlat: string;
  homePhoneNumber: string;
  workPhoneNumber: string;
  mobileNumber1: string;
  mobileNumber2: string;
  mobileNumber3: string;
}

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css'],
  providers: [MessageService]
})
export class ClientListComponent implements OnInit {
  clients: ClientRecord[];
  @Input() clientDetail: ClientDetail;
  display: boolean = false;
  EDITEMODE: boolean = false;
  selectedClient: ClientRecord;
  birthDate: number = Date.now();
  characters: SelectItem[];
  header: string;

  userform: FormGroup;

  submitted: boolean;
  valueChanged: boolean = false;

  name:string;
  radioTest: FormGroup;
  gender;

  constructor(private _service: ClientService, private fb: FormBuilder, private messageService: MessageService, fb1: FormBuilder) {
    this.characters = [
      {label: 'спокойный', value: 'спокойный'},
      {label: 'активный', value: 'активный'},
      {label: 'аккуратный', value: 'аккуратный'},
      {label: 'артистичный', value: 'артистичный'},
      {label: 'бдительный', value: 'бдительный'},
      {label: 'безобидный', value: 'безобидный'},
      {label: 'веселый', value: 'веселый'},
      {label: 'грозный', value: 'грозный'}
    ];

    this.name = 'Angular2'
    this.radioTest = fb1.group({
      gender: ['', Validators.required]
    });
  }

  ngOnInit() {
    this.userform = this.fb.group({
      'lastname': new FormControl('', Validators.required),
      'name': new FormControl('', Validators.required),
      'fatherName': new FormControl(''),
      'gender': new FormControl('', Validators.required),
      'bithDate': new FormControl('', Validators.required),
      'character': new FormControl('', Validators.required),
      'factStreet': new FormControl(''),
      'factNo': new FormControl(''),
      'factFlat': new FormControl(''),
      'regStreet': new FormControl('', Validators.required),
      'regNo': new FormControl('', Validators.required),
      'regFlat': new FormControl('', Validators.required),
      'homePhoneNumber': new FormControl(''),
      'workPhoneNumber': new FormControl(''),
      'mobileNumber1': new FormControl('', Validators.required),
      'mobileNumber2': new FormControl(''),
      'mobileNumber3': new FormControl('')
    });

    this.getClientRecords();
  }

  onSubmit(value: string) {
    this.submitted = true;
    this.messageService.add({severity:'info', summary:'Success', detail:'Form Submitted'});
  }

  getClientRecords(): void {
    this._service.getClientRecords().subscribe((content) => {
      this.clients = content;
    });
  }

  onSelect(c: ClientRecord) {
    console.log(c);
    this.selectedClient = c;
  }

  edit(id: number) {
    this.header = 'Редактирование клиента';
    this.EDITEMODE = true;
    this._service.getClientDetail(id).subscribe((content) => {
      this.clientDetail = content;
      console.log(content)
    });
    this.display = true;
  }

  add() {
    this.header = 'Добавление нового клиента';
    this.EDITEMODE = false;
    this.clientDetail = new ClientDetail();
    this.selectedClient = new ClientRecord();
    this.display = true;
  }

  cancel() {
    this.display = false;
  }

  // transformDate(date): string {
  //   var y = this.date.getFullYear();
  //   return ;
  // }

  saveClient1() {
    if (this.EDITEMODE) {
      this.selectedClient.character = this.clientDetail.character;
      this.selectedClient.lastName = this.clientDetail.lastName;
      this.selectedClient.name = this.clientDetail.name;
      this.selectedClient.fatherName = this.clientDetail.fatherName;
      // this.selectedClient.age = 2018 - (+this.clientDetail.birthDate.slice(6));

      // console.log(this.clientDetail.birthDate + ' selected: ' + this.transformDate(this.birthDate));

      this._service.updateClientDetail(this.clientDetail)
        .subscribe(() =>
          this._service.updateClientRecord(this.selectedClient)
            .subscribe(() => this.cancel())
        );
    } else {
      this.selectedClient.lastName = this.clientDetail.lastName;
      this.selectedClient.name = this.clientDetail.name;
      this.selectedClient.fatherName = this.clientDetail.fatherName;
      this.selectedClient.character = this.clientDetail.character;
      this.selectedClient.age = 20;
      this.selectedClient.totalBalance = 0.0;
      this.selectedClient.maxBalance = 0.0;
      this.selectedClient.minBalance = 0.0;

      this._service.addClientDetailes(this.clientDetail)
        .subscribe(() =>
          this._service.addClientRecord(this.selectedClient)
            .subscribe((c) => {
                this.clients.push(c);
                this.cancel();
              }
            )
        );
    }
    this.cancel();

  }

  deleteClient(id: number) {
    this.clients = this.clients.filter(c => c !== this.selectedClient);
    this._service.deleteClientDetails(id)
      .subscribe(() =>
        this._service.deleteClientRecord(id)
          .subscribe(() => this.cancel())
      );
  }

  closed() {
    console.log('closed');
    this.EDITEMODE = false;
    this.valueChanged = false;
  }

}
