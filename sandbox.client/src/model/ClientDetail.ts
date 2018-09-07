import {CharacterType} from "./CharacterType";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {Gender} from "./Gender";
import {Character} from "./Character";
/**
 * Created by msultanova on 9/5/18.
 */
export class ClientDetail{

  public surname: string;
  public name: string;
  public patronymic: string;
  public gender: Gender;
  public genders: Gender[];
  public character: Character;
  public characters: Character[];
  public actualAddress: Address;
  public registrationAddress: Address;
  public phones: Phone[];

  public static create(a: any): ClientDetail {
    debugger;
    const ret = new ClientDetail();
    ret.assign(a);
    return ret;
  }
  assign(a: any) {
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.gender = a.gender;
    this.genders = a.genders;
    this.character = a.character;
    this.characters = a.characters;
    this.actualAddress = a.actualAddress;
    this.registrationAddress = a.registrationAddress;
    this.phones = a.phones;
  }
}
