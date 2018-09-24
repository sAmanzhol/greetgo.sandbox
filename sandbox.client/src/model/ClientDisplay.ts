import {PhoneDisplay} from "./PhoneDisplay";

export class ClientDisplay {
  public id: string;
  public surname: string;
  public name: string;
  public patronymic: string;

  // fixme birthdate doljen byt' date
  public birthDate: string;
  public gender: string;
  public character: string;
  public streetRegistration: string;
  public houseRegistration: string;
  public apartmentRegistration: string;
  public streetResidence: string;
  public houseResidence: string;
  public apartmentResidence: string;

  public numbers: PhoneDisplay[];

  public static of(a: any): ClientDisplay {
    const ret = new ClientDisplay();
    ret.assign(a);
    return ret;
  }

  constructor() {
    this.id = "";
    this.surname = "";
    this.name = "";
    this.patronymic = "";
    this.birthDate = "";
    this.gender = "";
    this.character = "";
    this.streetRegistration = "";
    this.houseRegistration = "";
    this.apartmentRegistration = "";
    this.streetResidence = "";
    this.houseResidence = "";
    this.apartmentResidence = "";
    this.numbers = [];
  }

  assign(a: any) {
    this.id = a.id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.birthDate = a.birthDate;
    this.gender = a.gender;
    this.character = a.character;
    this.streetRegistration = a.streetRegistration;
    this.houseRegistration = a.houseRegistration;
    this.apartmentRegistration = a.apartmentRegistration;
    this.streetResidence = a.streetResidence;
    this.houseResidence = a.houseResidence;
    this.apartmentResidence = a.apartmentResidence;
    this.numbers = (a.numbers instanceof Array) ? a.numbers.map(c => c) : [];
  }
}
