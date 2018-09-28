import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ClientSaveService} from "./client-save.service";
import {PhonesService} from "../../phones/phones.service";
import {CharactersService} from "../../characters/characters.service";
import {ClientsComponent} from "../../clients/clients.component";
import {ClientToSave} from "../../../model/ClientToSave";
import {PhoneDisplay} from "../../../model/PhoneDisplay";

@Component({
  selector: 'app-client-info',
  templateUrl: './client-save.component.html',
  styleUrls: ['./client-save.component.css']
})
export class ClientSaveComponent implements OnInit {
  // fixme .07. Из списка на форму редактирования должен передоваться только ИД клиента.
  // fixme 1.07.1. ... а если осуществляется добавление клиента, то передаём null.

  @Input() inModalData: {};
  @Output() outModalData = new EventEmitter();

  closeResult: string;
  characterTypes = [];
  // fixme название переменной вводит в заблуждение. на первый взгляд кажется, что это список номеров, а не их типы. То же самое для character
  phoneTypes = [];
  client = new ClientToSave();

  @ViewChild('modal') modal: ElementRef;

  constructor(public Service: ClientSaveService, public ClientsComponent: ClientsComponent, public PhonesService: PhonesService, public CharactersService: CharactersService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.getCharacters();
    this.getPhoneTypes();
  }

  ngOnChanges() {
    if (this.inModalData["clientId"]) {
      this.getClient(this.inModalData["clientId"]);
    } else {
      this.client = new ClientToSave();
    }

    setTimeout(() => this.modalService.open(this.modal, {size: 'lg'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${reason}`;
    }));
  }

  onSubmit() {
    this.crupdate(this.client);
  }

  addPhone() {
    this.client["numbers"].push(new PhoneDisplay());
  }

  changePhoneType(phone, type) {
    phone.type = type;
  }

  deletePhone(index) {
    this.client["numbers"].splice(index, 1)
  }

  closeModal() {
    this.inModalData = {
      "clientId": null
    };

    this.outModalData.emit(this.inModalData);
    this.modalService.dismissAll();
  }

  async getClient(id) {
    //fixme Нужны ли везде эти трай кетчи?
    this.client = await this.Service.getClient(id);
    console.log(this.client);
  }

  async crupdate(clientToSave) {
    await this.Service.crupdateClient(clientToSave);
    this.closeModal();
    this.ClientsComponent.loadPage();
  }

  async getCharacters() {
    this.characterTypes = await this.CharactersService.getCharacters();
    console.log(this.characterTypes);
  }

  async getPhoneTypes() {
    this.phoneTypes = await this.PhonesService.getPhoneTypes();
  }
}
