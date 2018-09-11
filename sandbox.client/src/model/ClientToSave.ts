import {Gender} from "./Gender";
import {Character} from "./Character";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {ClientDetail} from "./ClientDetail";

export class ClientToSave{
  public clientID: number/*long*/;
  public surname: string;
  public name: string;
  public patronymic:string ;
  public gender: Gender;
  public birthDay: string;
  public character: Character;
  public actualAddress: Address;
  public registrationAddress: Address;
  public phones: Phone[];

  static create(clientDetail: ClientDetail, id: number): ClientToSave {
    //debugger;
    const ret = new ClientToSave();
    ret.assign(clientDetail, id);
    return ret;
  }
  assign(a: ClientDetail, id: number) {
    this.clientID = id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.gender = a.gender;
    this.birthDay = a.birthDay;
    this.character = a.character;
    this.actualAddress = a.actualAddress;
    this.registrationAddress = a.registrationAddress;
    this.phones = a.phones;
  }
}
