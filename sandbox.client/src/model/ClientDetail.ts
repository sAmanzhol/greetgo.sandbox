import {Address} from "./Address";
import {Phone} from "./Phone";
import {Gender} from "./Gender";
import {Character} from "./Character";
import {PhoneDetail} from "./PhoneDetail";

export class ClientDetail {

  public surname: string;
  public name: string;
  public patronymic: string;
  public age: number/*int*/;
  public gender: Gender;
  public genders: Gender[];
  public birthDay: Date;
  public character: Character;
  public characters: Character[];
  public actualAddress: Address = new Address();
  public registrationAddress: Address = new Address();
  public phones: Phone[];
  public phoneDetailList: PhoneDetail[];
  public clientId: number/*long*/;


  public static create(a: any): ClientDetail {
    const ret = new ClientDetail();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    if (a.surname) {
      this.surname = a.surname;
    }
    if (a.name) {
      this.name = a.name;
    }
    if (a.patronymic) {
      this.patronymic = a.patronymic;
    }
    if (a.gender) {
      this.gender = a.gender;
    }
    if (a.genders) {
      this.genders = a.genders;
    }
    if (a.birthDay) {
      this.birthDay = a.birthDay;
    }
    if (a.character) {
      this.character = a.character;
    }
    if (a.characters) {
      this.characters = a.characters;
    }
    if (a.actualAddress) {
      this.actualAddress = a.actualAddress;
    }
    if (a.registrationAddress) {
      this.registrationAddress = a.registrationAddress;
    }
    if (a.phones) {
      this.phones = a.phones;
    }
    if (a.clientId) {
      this.clientId = a.clientId;
    }
    if (a.phoneDetailList) {
      this.phoneDetailList = a.phoneDetailList;
    }
    if (a.age) {
      this.age = a.age;
    }
  }
}
