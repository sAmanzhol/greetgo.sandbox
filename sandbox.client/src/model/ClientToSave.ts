export class ClientToSave {
  public id: string;
  public surname: string;
  public name: string;
  public patronymic: string;
  public birthDate: string;
  public gender: string;
  public character: string;
  public streetRegistration: string;
  public houseRegistration: string;
  public apartmentRegistration: string;
  public streetResidence: string;
  public houseResidence: string;
  public apartmentResidence: string;
  public phoneHome: string;
  public phoneWork: string;
  public phoneExtra3: string;
  public phoneExtra4: string;
  public phoneExtra5: string;

  public static of(a: any): ClientToSave {
    const ret = new ClientToSave();
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
    this.phoneHome = "";
    this.phoneWork = "";
    this.phoneExtra3 = "";
    this.phoneExtra4 = "";
    this.phoneExtra5 = "";
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
    this.phoneHome = a.phoneHome;
    this.phoneWork = a.phoneWork;
    this.phoneExtra3 = a.phoneExtra3;
    this.phoneExtra4 = a.phoneExtra4;
    this.phoneExtra5 = a.phoneExtra5;
  }
}
