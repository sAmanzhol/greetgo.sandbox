import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";
import {ConfirmationService, LazyLoadEvent, SelectItem} from "primeng/api";
import {Validators, FormControl, FormGroup, FormBuilder} from "@angular/forms";
import {FilterParams} from "../../model/FilterParams";

export class ClientDetail {
  id: number;
  lastName: string;
  name: string;
  fatherName: string;
  fullName: string;
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

export class ClientRecord {
  id: number;
  lastName: string;
  name: string;
  fatherName: string;
  fullName: string;
  character: string;
  age: number;
  totalBalance: number;
  maxBalance: number;
  minBalance: number;
}

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css'],
  providers: [ConfirmationService]
})
export class ClientListComponent implements OnInit {
  clients: ClientRecord[];
  datasource: ClientRecord[];
  @Input() clientDetail: ClientDetail;
  display: boolean = false;
  EDITEMODE: boolean = false;
  selectedClient: ClientRecord;
  birthDate: number = Date.now();
  characters: SelectItem[];
  header: string;
  symbols: RegExp = /^[a-zA-Z а-яА-Я]+$/;
  cols: any[];
  nameCols: any[];
  clientform: FormGroup;
  totalRecords: number;
  loading: boolean;
  private filterParams: FilterParams = new FilterParams();

  constructor(private _service: ClientService, private fb: FormBuilder, private confirmationService: ConfirmationService) {
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
  }

  ngOnInit() {
    this.cols = [
      {field: 'fullName', header: 'ФИО'},
      {field: 'character', header: 'Характер'},
      {field: 'age', header: 'Возраст'},
      {field: 'totalBalance', header: 'Общий остаток счетов'},
      {field: 'maxBalance', header: 'Максимальный остаток'},
      {field: 'minBalance', header: 'Минимальный остаток'}
    ];
    this.nameCols = [
      {field: 'lastName'},
      {field: 'name'},
      {field: 'fatherName'}
    ];
    this.getClientRecords();
    this.setValidators();
    this.loading = true;
  }

  loadCarsLazy(event: LazyLoadEvent) {
    this.loading = true;
    setTimeout(() => {
      if (this.datasource) {
        this.clients = this.datasource.slice(event.first, (event.first + event.rows));
        this.loading = false;
      }
    }, 1000);
  }

  setValidators() {
    this.clientform = this.fb.group({
      'lastName': new FormControl('', Validators.required),
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
  }

  getClientRecords(): void {

    this.filterParams.sortBy='asdas';

    this._service.getClientRecords(this.filterParams).subscribe((content) => {

      console.log(content)
      this.datasource = content;
      this.totalRecords = this.datasource.length;
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
    if (this.clientform.dirty) {
      alert('Закрыть без сохранения?');
    } else {
      this.close();
    }
  }

  close() {
    this.display = false;
  }

  setClientRecord() {
    this.selectedClient.lastName = this.clientDetail.lastName;
    this.selectedClient.name = this.clientDetail.name;
    this.selectedClient.fatherName = this.clientDetail.fatherName;
    this.clientDetail.fullName = this.clientDetail.lastName + ' ' + this.clientDetail.name + ' ' + this.clientDetail.fatherName;
    this.selectedClient.fullName = this.clientDetail.fullName;
    this.selectedClient.character = this.clientDetail.character;
    this.selectedClient.age = (new Date()).getFullYear() - (+this.clientDetail.birthDate.slice(6));
  }

  saveClient1() {
    if (this.EDITEMODE) {
      this.setClientRecord();

      this._service.updateClientDetail(this.clientDetail)
        .subscribe(() =>
          this._service.updateClientRecord(this.selectedClient)
            .subscribe(() => this.close())
        );
    } else {
      this.setClientRecord();
      this.selectedClient.totalBalance = 0.0;
      this.selectedClient.maxBalance = 0.0;
      this.selectedClient.minBalance = 0.0;

      this._service.addClientDetailes(this.clientDetail)
        .subscribe(() =>
          this._service.addClientRecord(this.selectedClient)
            .subscribe((c) => {
                this.clients.push(c);
                this.close();
              }
            )
        );
    }
  }

  confirm(id: number) {
    this.confirmationService.confirm({
      message: 'Удалить клиент ' + this.selectedClient.fullName + '?',
      header: 'Удаление',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.deleteClient(id);
      },
      reject: () => {
      }
    });

  }

  deleteClient(id: number) {
    this.clients = this.clients.filter(c => c !== this.selectedClient);
    this._service.deleteClientDetails(id)
      .subscribe(() =>
        this._service.deleteClientRecord(id)
          .subscribe());
  }

  onSubmit() {
    if (this.clientform.valid) {
      console.log("Form Submitted!");
      this.clientform.reset();
    }
  }

}
