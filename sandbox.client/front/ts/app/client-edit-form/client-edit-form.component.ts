import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";
import "rxjs/add/operator/toPromise";
import {Component, EventEmitter, OnInit, Output,} from "@angular/core";
import {HttpService} from "../HttpService";
import {ClientRecord} from "../../model/ClientRecord";
import {ClientDetails} from "../../model/ClientDetails";
import {GenderType} from "../../model/GenderType";
import {Charm} from "../../model/Charm";
import {PhoneType} from "../../model/PhoneType";
import {ClientPhone} from "../../model/ClientPhone";
import {ClientToSave} from "../../model/ClientToSave";


@Component({
  selector: 'client-edit-form',
  template: require('./client-edit-form.component.html'),
  styles: [require('./client-edit-form.component.css')],
})
export class ClientEditFormComponent implements OnInit {
  constructor(private http: HttpService) {}
  ngOnInit() {
    this.isOpen=false;
  }

  @Output() onChangedClientRecord = new EventEmitter<ClientRecord>();

  clientDetails: ClientDetails = new ClientDetails();
  genders: GenderType[] = [GenderType.MALE, GenderType.FEMALE];
  charm: Charm[] = [];
  phoneType: PhoneType[] = [PhoneType.HOME, PhoneType.WORK, PhoneType.MOBILE, PhoneType.EMBEDDED];
  titleName: string;
  isOpen:boolean=false;


  getCharm() {
    var self = this;
    this.http.get('/client/client-charm').subscribe(data => {
      self.charm = data.json();
    })
  }

  editablePhoneNumberOfClientDetails(isNewNumber: boolean, noNumber: number) {

    if (isNewNumber === true) {

      this.clientDetails.phone.push(new ClientPhone());
    }
    else if (isNewNumber == false) {
      if (this.clientDetails.phone.length > 1) {

        this.clientDetails.phone.splice(noNumber, 1);
      }
      else {


      }
    }
  }

  addOrEdit() {
    var self = this;
    let clientToSave: ClientToSave = new ClientToSave();
    clientToSave.assign(self.clientDetails);
    this.http.get('/client/client-details-save', {clientToSave: JSON.stringify(clientToSave)}).subscribe(data => {
      self.onChangedClientRecord.emit(data.json());
      self.closeForm();
    })
  }

  closeForm() {
    this.isOpen=false;
  }

  getCharmById() {
    let self = this;
    let isFind: boolean = false;
    for (let i = 0; i < self.charm.length; i++) {
      if (self.charm[i].id == self.clientDetails.characterId) {
        isFind = true;
        console.log("NOT FOUND")
      }
    }
    if (!isFind) {
      this.http.get('/client/client-add-charm-id', {charmId: self.clientDetails.characterId}).subscribe(data => {

        self.charm.push(Charm.copy(data.json()))

      })
    }

  }


  showAddFormOrEditForm(clientId) {

    var self = this;
    self.isOpen=true;
    self.titleName = "Добавить";
    this.clientDetails = new ClientDetails();
    self.getCharm();
    if (clientId) {
      self.titleName = "Редактировать";
      this.http.get('/client/client-details-set', {clientMark: clientId}).subscribe(data => {
          self.clientDetails = ClientDetails.copy(data.json());
          self.getCharmById();
        }
      )
    }

  }


}

