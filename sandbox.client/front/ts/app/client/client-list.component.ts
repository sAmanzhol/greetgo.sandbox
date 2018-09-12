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
import {ClientToSave} from "../../model/ClientToSave";
import {isNumber} from "util";


@Component({
  selector: 'client-list',
  template: require('./client-list.component.html'),
  styles: [require('./client-list.component.css')],
})
export class ClientListComponent implements OnInit {
  constructor(private http: HttpService) {
  }

  editButtonOrAddButton: boolean = true;
  showAddOrEditChoose: boolean;
  clientRecord: ClientRecord[] = [new ClientRecord()];
  clientMark: ClientRecord;
  clientFilter: ClientFilter;
  clientDetails: ClientDetails = new ClientDetails();
  genders: GenderType[] = [GenderType.MALE, GenderType.FEMALE];
  charm: Charm[] = [];
  phoneType: PhoneType[] = [PhoneType.HOME, PhoneType.WORK, PhoneType.MOBILE, PhoneType.EMBEDDED]
  recordTotal: number;


  ngOnInit() {
    this.clientFilter = new ClientFilter();
    this.getClient();
    this.getTotalRecord();

  }


  getTotalRecord() {
    var self = this;
    this.http.get('/client/client-total-record', {clientFilter: JSON.stringify(self.clientFilter)}).subscribe(data => {
      self.recordTotal = data.json();
      self.clientFilter.recordTotal = self.recordTotal;
      self.clientFilter.pageTotal = Math.floor(self.clientFilter.recordTotal / self.clientFilter.recordSize);
    })
  }


  getClient() {
    let self = this;
    this.http.get("/client/client-list", {clientFilter: JSON.stringify(self.clientFilter)}).subscribe((data) => {
      self.clientRecord = data.json();
    })
  }


  getClientFilterPagination(page) {
    let self = this;    self.clientFilter.page = page;
    if (self.clientFilter.page > self.clientFilter.pageTotal) {
      self.clientFilter.page = self.clientFilter.pageTotal;
    }
    this.http.get('/client/client-list', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();

      });


  }

  getClientFilterFilter(clienfilter) {
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

  getClientFilterSort(orderBy: string) {
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

    let id = self.clientMark.id;
    this.http.get('/client/client-details-delete', {clientMark: self.clientMark.id}).subscribe(data => {
      for (let i = 0; i < self.clientRecord.length; i++) {
        if (self.clientRecord[i].id == id) {
          self.clientRecord.splice(i, 1)
          i--;
        }
      }
    })
    self.clientMark=new ClientRecord();
  }
   clientMark1:ClientRecord;

  addOrEdit() {
    var self = this;
    let clientToSave: ClientToSave = new ClientToSave();
    clientToSave.assign(self.clientDetails);
    this.http.get('/client/client-details-save', {clientToSave: JSON.stringify(clientToSave)}).subscribe(data => {
      var clientRec = data.json();
      if (!self.clientDetails.id) {
        self.clientRecord.push(clientRec);

      }
      else if (self.clientDetails.id) {
        for (let i = 0; i < self.clientRecord.length; i++) {
          if (self.clientRecord[i].id == data.json().id) {
            self.clientRecord.splice(i, 1, clientRec);

          }
        }
      }

      self.clientMark=clientRec;
    })



  }

  editablePhoneNumberOfClientDetails(isNewNumber:boolean, noNumber: number) {

    if (isNewNumber===true) {

      this.clientDetails.phone.push(new ClientPhone());
    }
    else if(isNewNumber==false) {
      if (this.clientDetails.phone.length > 1) {

        this.clientDetails.phone.splice(noNumber, 1);
      }
      else {


      }
    }
  }

  getMarkClient(clientRecord) {
    this.clientMark = clientRecord;

  }

  getCharm() {
    var self = this;
    this.http.get('/client/client-charm').subscribe(data => {
      self.charm = data.json();

    })
  }


  showAddFormOrEditForm(isEditOrAdd) {
    var self = this;

    this.clientDetails=new ClientDetails();
    self.showAddOrEditChoose = isEditOrAdd;
    self.getCharm();
    if (self.showAddOrEditChoose) {
      this.editButtonOrAddButton = true;
      self.clientMark = new ClientRecord();

    }
    else if (!self.showAddOrEditChoose) {
      this.editButtonOrAddButton = false;
      this.http.get('/client/client-details-set', {clientMark: self.clientMark.id}).subscribe(data => {
          self.clientDetails = ClientDetails.copy(data.json());

      }
      )
    }
  }


}

