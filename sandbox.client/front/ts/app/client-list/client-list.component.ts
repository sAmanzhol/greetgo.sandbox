import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Component, OnInit, ViewChild,} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";
import {PhoneType} from "../../model/PhoneType";
import {ClientEditFormComponent} from "../client-edit-form/client-edit-form.component";


@Component({
  selector: 'client-list',
  template: require('./client-list.component.html'),
  styles: [require('./client-list.component.css')],
})
export class ClientListComponent implements OnInit {
  constructor(private http: HttpService) {
  }

  @ViewChild(ClientEditFormComponent) clientEditFormComponent: ClientEditFormComponent;

  clientRecord: ClientRecord[] = [new ClientRecord()];
  clientOnSelect: ClientRecord = new ClientRecord();
  clientFilter: ClientFilter;
  phoneType: PhoneType[] = [PhoneType.HOME, PhoneType.WORK, PhoneType.MOBILE, PhoneType.EMBEDDED]


  ngOnInit() {
    this.clientFilter = new ClientFilter();
    this.getClient();
    this.getTotalRecord();
  }

  getTotalRecord() {

    var self = this;
    this.http.get('/client/client-total-record', {clientFilter: JSON.stringify(self.clientFilter)}).subscribe(data => {

      self.clientFilter.recordTotal = data.json();
      self.clientFilter.pageTotal = Math.floor(self.clientFilter.recordTotal / self.clientFilter.recordSize);
    })
  }

  getClient() {
    let self = this;
    this.http.get("/client/client-list", {clientFilter: JSON.stringify(self.clientFilter)}).subscribe((data) => {
      self.clientRecord = data.json();
    })
  }
  getReport() {
    let self = this;
    this.http.url("/report/my_big_report")
  }

  changePagination(page) {
    let self = this;
    self.clientFilter.page = page;
    if (self.clientFilter.page > self.clientFilter.pageTotal) {
      self.clientFilter.page = self.clientFilter.pageTotal;
    }
    this.http.get('/client/client-list', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();

      });

  }

  filter(clienfilter) {
    let self = this;
    if (clienfilter) {
      self.clientFilter.firstname = clienfilter.firstname;
      self.clientFilter.lastname = clienfilter.lastname;
      self.clientFilter.patronymic = clienfilter.patronymic;
    }
    self.clientFilter.page = 0;
    this.http.get('/client/client-list', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getTotalRecord();
      });

  }

  sort(orderBy: string) {
    let self = this;
    this.clientFilter.orderBy = orderBy;
    this.clientFilter.sort = !this.clientFilter.sort;
    this.http.get('/client/client-list', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
      })
  }


  deleteClient() {
    var self = this;

    let id = self.clientOnSelect.id;
    this.http.get('/client/delete-client', {clientMark: self.clientOnSelect.id}).subscribe(data => {
      for (let i = 0; i < self.clientRecord.length; i++) {
        if (self.clientRecord[i].id == id) {
          self.clientRecord.splice(i, 1)
          i--;
        }
      }
    })
    self.clientOnSelect = new ClientRecord();
  }


  onSelect(clientRecord) {
    this.clientOnSelect = clientRecord;
    console.log(this.clientOnSelect);

  }

  changeClientRecord(clientRec) {
    var self = this;
    console.log(clientRec.id + "CLIENTDETAILS")
    let isFind: boolean = true;
    for (let i = 0; i < self.clientRecord.length; i++) {
      if (clientRec.id == self.clientRecord[i].id) {
        self.clientRecord.splice(i, 1, clientRec);
        isFind = false;
      }

    }
    if (isFind) {
      self.clientRecord.push(clientRec);
    }

    self.clientOnSelect = clientRec

  }

  showAddFormOrEditForm(isEditOrAdd) {
    var self = this;
    let clientId: number | null;
    if (!isEditOrAdd) {
      clientId = self.clientOnSelect.id
    }

    self.clientEditFormComponent.showAddFormOrEditForm(clientId);
  }


}

