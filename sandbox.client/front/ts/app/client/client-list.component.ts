import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Component, OnInit,} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientFilter} from "../../model/ClientFilter";
import {ClientDetails} from "../../model/ClientDetails";
import {GenderType} from "../../model/GenderType";
import {Charm} from "../../model/Charm";
import {PhoneType} from "../../model/PhoneType";
import {ClientPhone} from "../../model/ClientPhone";


@Component({
  selector: 'client-list',
  template: require('./client-list.component.html'),
  styles: [require('./client-list.component.css')],
})
export class ClientListComponent implements OnInit {
  constructor(private http: HttpService) {
  }

  editButtonOrAddButton: boolean = true;
  clientChoose: boolean = false;
  clientRecord: ClientRecord[] = [new ClientRecord()];
  clientMark: ClientRecord;
  clientFilter: ClientFilter = new ClientFilter();
  clientDetails: ClientDetails = new ClientDetails();
  genders: GenderType[] = [GenderType.MALE, GenderType.FEMALE];
  charm: Charm[] = [new Charm()];
  phoneType: PhoneType[] = [PhoneType.HOME, PhoneType.WORK, PhoneType.MOBILE, PhoneType.EMBEDDED]

  ngOnInit() {
    this.getClient();
    this.getCharm();
  }

  addNewPhone() {
    this.clientDetails.phone.push(new ClientPhone());
  }

  getMarkClient(clientRecord) {
    this.clientMark = clientRecord;
    this.clientChoose = true;
    console.log(this.clientMark);
  }

  showEditForm() {
    var self = this;
    this.editButtonOrAddButton = false;
    this.http.get('/client/client-details-set', {clientMark: JSON.stringify(self.clientMark)}).subscribe(data => {
        self.clientDetails = data.json();
        self.getCharm();
      }
    )
  }

  showAddForm() {
    var self = this;
    this.editButtonOrAddButton = true;
    self.getCharm();
  }

  deleteClient() {
    var self = this;
    let id = self.clientMark.id;
    this.http.get('/client/client-details-delete', {clientMark: JSON.stringify(self.clientMark)}).subscribe(data => {
      for (let i = 0; i < self.clientRecord.length; i++) {
        if (self.clientRecord[i].id == id) {
          self.clientRecord.splice(i, 1)
          i--;
        }
      }
    })

  }

  editClient() {
    var self = this;
    let dates: ClientRecord;
    this.http.get('/client/client-details-save', {clientDetails: JSON.stringify(self.clientDetails)}).subscribe(data => {
      dates = data.json();
      for (let i = 0; i < self.clientRecord.length; i++) {
        if (self.clientRecord[i].id == dates.id) {
          self.clientRecord.splice(i, 1, dates);

        }
      }
    })

  }

  addClient() {
    var self = this;
    self.clientDetails.id = 0;
    for (let i = 0; i < self.charm.length; i++) {
      if (self.clientDetails.character.id == self.charm[i].id) {
        self.clientDetails.character = self.charm[i];
      }
    }
    this.http.get('/client/client-details-save', {clientDetails: JSON.stringify(self.clientDetails)}).subscribe(data => {
      self.clientRecord.push(data.json());

    })

  }


  getCharm() {
    var self = this;
    this.http.get('/client/client-charm').subscribe(data => {
      self.charm = data.json();
    })
  }

  getClientFilterPagination(page) {
    let self = this;
    self.clientFilter.page = page;
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        self.getClientFilter();
      });


  }

  getClientFilterFilter() {
    let self = this;
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getClientFilter();

      });

  }

  getClientFilterSort(orderBy: string, sort: boolean) {
    let self = this;
    this.clientFilter.orderBy = orderBy;
    this.clientFilter.sort = !sort;
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getClientFilter();
      })
  }

  getClientFilter() {
    var self = this;
    this.http.get('/client/client-filter-set').subscribe(data => {
      self.clientFilter = data.json();
      this.clientChoose = false;
    })
  }

  getClient() {
    let self = this;
    this.http.get("/client/client-list").subscribe((data) => {
      self.clientRecord = data.json();
      this.getClientFilter();
    })
  }
}

