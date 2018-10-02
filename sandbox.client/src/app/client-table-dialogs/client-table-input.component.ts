import {Component, Inject, OnInit} from '@angular/core';
import {Phone} from "../../model/Phone";
import {GenderType} from "../../model/GenderType";
import {Charm} from "../../model/Charm";
import {Address} from "../../model/Address";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ButtonModule} from 'primeng/button';
import {MessagesModule} from 'primeng/messages'
import {DialogModule} from 'primeng/dialog'
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputTextModule} from 'primeng/inputtext';
import {LoginService} from "../login/login.service";
import {ClientRepositoryService} from "../client-repository/client-repository.service";
import {ClientRecord} from "../../model/ClientRecord";
import {SelectItem} from 'primeng/api';
import {ClientTableComponent} from "../client-table/client-table.component";
import {InputMaskModule} from 'primeng/inputmask';

@Component({
  selector: 'app-client-table-input',
  templateUrl: './client-table-input.component.html',
  styleUrls: ['./client-table-input.component.css']
})

export class ClientTableInputComponent{
  displayDialog : boolean;
  client:ClientRecord;
  tempClient : ClientRecord;
  charmList : Charm[];

  public invoke(id:number){
    this.client = ClientRecord.createEmpty();

    if(id != null)
      this.tempClient= this.clientRepoService.getById(id);
    else
      this.tempClient = null;

    if(this.tempClient !== null)
      this.client.assign(this.tempClient);

    this.displayDialog = true;
  }

  public cancel(){
    this.client = null;
    this.displayDialog = false;
  }

  public save(){
    if(this.client.id !== undefined)
      this.clientRepoService.update(this.client);
    else
      this.clientRepoService.create(this.client);

    this.displayDialog = false;
  }

  public delete(id:number){
    this.client = this.clientRepoService.getById(id);
    this.clientRepoService.delete(this.client);
  }

  public create(){
    this.clientRepoService.create(this.client);
  }

  constructor(public login: LoginService,
              public clientRepoService: ClientRepositoryService) {
    this.charmList = clientRepoService.charmList;
  }
}

