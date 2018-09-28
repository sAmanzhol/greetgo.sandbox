import {Component, Inject, OnInit} from '@angular/core';
import {Phone} from "../../model/Phone";
import {GenderType} from "../../model/GenderType";
import {Charm} from "../../model/Charm";
import {Address} from "../../model/Address";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {ButtonModule} from 'primeng/button';
import {MessagesModule} from 'primeng/messages'
import {DialogModule} from 'primeng/dialog'
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'app-client-table-input',
  templateUrl: './client-table-input.component.html',
})

export class ClientTableInputComponent {

   surname: string;
   name: string;
   patronomic: string;
   gender: GenderType;
   birthDate:string;
   charm: Charm = new Charm();
   adressP:Address = new Address();
   adressR:Address = new Address();
   phone:Phone = new Phone();


  display: boolean = false;

  showDialog() {
    this.display = true;
  }
  //Отклонение операции
  decline(){

  }

  //Принятие
  accept(){

  }

}

