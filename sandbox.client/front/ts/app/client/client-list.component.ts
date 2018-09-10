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
  clientFilter: ClientFilter;
  clientDetails: ClientDetails = new ClientDetails();
  genders: GenderType[] = [GenderType.MALE, GenderType.FEMALE];
  charm: Charm[] = [];
  phoneType: PhoneType[] = [PhoneType.HOME, PhoneType.WORK, PhoneType.MOBILE, PhoneType.EMBEDDED]
  recordTotal:number;

  ngOnInit() {
    this.clientFilter = new ClientFilter();
    this.getClient();
    this.getTotalRecord();
    this.getCharm()
  }


  getTotalRecord() {
    var self = this;
   this.http.get('/client/client-filter-set').subscribe(data=>{
     self.recordTotal=data.json();
     self.clientFilter.recordTotal=self.recordTotal;
     self.clientFilter.pageTotal= Math.floor(self.clientFilter.recordTotal/self.clientFilter.recordSize);
   })
  }


  getClient() {
    let self = this;
    this.http.get("/client/client-filter", {clientFilter: JSON.stringify(self.clientFilter)}).subscribe((data) => {
      self.clientRecord = data.json();
    })
  }


  getClientFilterPagination(page) {
    let self = this;
    self.clientFilter.page = page;
    if(self.clientFilter.page>self.clientFilter.pageTotal){
      self.clientFilter.page=self.clientFilter.pageTotal;
    }
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getTotalRecord();
      });


  }

  getClientFilterFilter(clienfilter) {
    let self = this;
    if(clienfilter){
    self.clientFilter.firstname=clienfilter.firstname;
    self.clientFilter.lastname=clienfilter.lastname;
    self.clientFilter.patronymic=clienfilter.patronymic;}
    self.clientFilter.page=0;
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getTotalRecord();
      });

  }

  getClientFilterSort(orderBy: string, sort: boolean) {
    let self = this;
    this.clientFilter.orderBy = orderBy;
    this.clientFilter.sort = !sort;
    this.http.get('/client/client-filter', {clientFilter: JSON.stringify(self.clientFilter)})
      .subscribe(data => {
        self.clientRecord = data.json();
        this.getTotalRecord();
      })
  }

  deleteClient() {
    var self = this;
    self.clientChoose=false;
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
    // self.clientDetails.id = null;
    console.log(self.clientDetails.characterId + "THIS ID OF CHRACTER");
    this.http.get('/client/client-details-save', {clientDetails: JSON.stringify(self.clientDetails)}).subscribe(data => {
      self.clientRecord.push(data.json());

    })

  }

  editablePhoneNumberOfClientDetails(sum: number) {
    if (sum == +1)
      this.clientDetails.phone.push(new ClientPhone());
    else {
      if (this.clientDetails.phone.length > 1) {
        console.log('toto')
        this.clientDetails.phone.splice(-1, 1);
      }
      else {
        console.log(this.clientDetails.phone);
      }
    }
  }

  getMarkClient(clientRecord) {
    this.clientMark = clientRecord;
    this.clientChoose = true;
    console.log(window.location.href);
    console.log(this.clientMark);
  }

  getCharm() {
    var self = this;
    this.http.get('/client/client-charm').subscribe(data => {
      self.charm = data.json();
      console.log("dasdsa");


    })
  }

  showEditForm() {
    var self = this;
    this.editButtonOrAddButton = false;
    this.clientChoose=false;
    this.http.get('/client/client-details-set', {clientMark: JSON.stringify(self.clientMark)}).subscribe(data => {
        self.clientDetails = data.json();
        self.getCharm();
      }
    )
  }

  showAddForm() {
    this.clientChoose=false;
    var self = this;
    this.editButtonOrAddButton = true;
    self.getCharm();
    self.clientMark=new ClientRecord();
    self.clientDetails=new ClientDetails();
  }





}

