import {AfterViewInit, ChangeDetectorRef, Component, Output} from '@angular/core';
import {Phone} from "../../model/Phone";
import {Charm} from "../../model/Charm";
import {Address} from "../../model/Address";
import {LoginService} from "../login/login.service";
import {ClientRepositoryService} from "../client-repository/client-repository.service";
import {ClientRecord} from "../../model/ClientRecord";
import {PhoneType} from "../../model/PhoneType";
import {AddressType} from "../../model/AddressType";
import {GenderType} from "../../model/GenderType";

@Component({
  selector: 'app-client-table-input',
  templateUrl: './client-table-input-forms.component.html',
  styleUrls: ['./client-table-input-forms.component.css']
})

export class ClientTableInputFormsComponent{

  //TODO default value of address and phone не работает
  //TODO валидация даты: Дата не может быть больше текущего дня

  displayDialog: boolean;
  displayDeleteDialog: boolean;
  client: ClientRecord;
  tempClient: ClientRecord;
  charmList: Charm[];
  phoneTypes;
  addressTypes;
  currPhoneType;
  currAddressType;
  errorMes = "";

  phoneType = PhoneType;
  addressType = AddressType;
  genderType =  GenderType;
  displayNotificationDialog: boolean;

  constructor(public login: LoginService, public clientRepoService: ClientRepositoryService) {
    this.loadCharms();
    this.phoneTypes = clientRepoService.phoneTypes;
    this.addressTypes = clientRepoService.addressTypes;
  }

  public async invoke(id: number) {
    this.client = ClientRecord.createEmpty();

    if (id != null)
      this.tempClient = await this.clientRepoService.getClientById(id);
    else
      this.tempClient = null;

    if (this.tempClient !== null)
      this.client.copyAssign(this.tempClient);

    this.displayDialog = true;
  }

  public cancel() {
    this.client = null;
    this.displayDialog = false;
  }

  public save() {

    //TODO сделать валидацию на номера телефонов

    this.errorMes = null;
    this.errorMes = this.client.checkRequiredFields();

    if(!this.errorMes) {

      if (this.client.id !== undefined)
        this.clientRepoService.update(this.client);
      else
        this.clientRepoService.create(this.client);

      this.displayDialog = false;
    }
    else
    {
      this.displayNotificationDialog = true;
    }
  }

  public async delete(id: number) {
    this.displayDeleteDialog = true;
    this.client = await this.clientRepoService.getClientById(id);
  }

  public approveDelete(isApproved: boolean) {
    if (isApproved)
      this.clientRepoService.delete(this.client);

    this.displayDeleteDialog = false;
  }

  public create() {
    this.clientRepoService.create(this.client);
  }

  public async loadCharms() {
    this.charmList = await this.clientRepoService.getCharmlist();
  }

  public addNewPhone() {
    if (this.currPhoneType)
      this.client.phones.push(new Phone(this.currPhoneType.value) );
    this.currPhoneType = null;
  }

  public setNewPhoneType(event) {
    this.currPhoneType = event.value;
  }

  public setNewAddressType(event: any) {
    this.currAddressType = event.value;
  }

  public addNewAddress() {
    if (this.currAddressType)
      this.client.addresses.push(new Address(this.currAddressType.value));
    this.currAddressType = null;
  }

}

