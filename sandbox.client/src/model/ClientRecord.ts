import {GenderType} from "./GenderType";
import {Charm} from "./Charm";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {formatDate} from "@angular/common";


export class ClientRecord {
  public id: number;
  public surname: string;
  public name: string;
  public patronymic: string;
  public birthDate: any;
  public gender: GenderType;
  public charm: Charm;
  public addresses: Address[] = [];
  public phones: Phone[] = [];
  public maxbal: number;
  public minbal: number;
  public sumbal: number;
  public actual: boolean;

  public static NO_ACCOUNT_MESSAGE = "Нет счета";

  public static create(a: any): ClientRecord {
    const ret = new ClientRecord();
    ret.assign(a);
    return ret;
  }

  public assign(a: any) {
    this.id = a.id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.birthDate = formatDate(new Date(parseInt(a.birthDate)), "dd-MM-yyyy", "en-IN").toString();
    this.gender = a.gender == GenderType.FEMALE ? GenderType.FEMALE : GenderType.MALE;
    this.maxbal = a.maxbal;
    this.minbal = a.minbal;
    this.sumbal = a.sumbal;
    this.actual = a.actual;

    if (a.addresses) {
      a.addresses.forEach(adr => {
        this.addresses.push(Address.create(adr));
      });
    }

    if (a.charm) {
      this.charm = Charm.create(a.charm);
    }

    if (a.phones) {
      a.phones.forEach(phn => {
          this.phones.push(phn);
        }
      );
    }
  }

  public copyAssign(a: any) {
    this.id = a.id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.birthDate = a.birthDate;
    this.gender = a.gender == GenderType.MALE ? GenderType.MALE : GenderType.FEMALE;
    this.charm = a.charm;
    this.maxbal = a.maxbal;
    this.minbal = a.minbal;
    this.sumbal = a.sumbal;
    this.phones = a.phones;
    this.addresses = a.addresses;
    this.actual = a.actual;
  }

  public static createEmpty(): ClientRecord {
    let empty = new ClientRecord();
    empty.id = undefined;
    empty.surname = "";
    empty.name = "";
    empty.patronymic = "";
    empty.birthDate = "01-01-1991";
    empty.gender = GenderType.MALE;
    empty.phones = [];
    empty.actual = true;
    return empty;
  }

  public checkRequiredFields(): string {
    let msg = "";

    if (!this.surname)
      msg += "Фамилия,";
    if (!this.name)
      msg += "Имя,";
    if(!this.birthDate)
      msg += "Дата рождения,";
    if (!this.charm)
      msg += "Характер,";
    if (!this.addresses || this.addresses.length == 0)
      msg += "Адрес,";
    if (!this.phones || this.phones.length == 0)
      msg += "Телефон,";

    return (msg.length != 0?msg:null);
  }

  public getAge() {
    let ageDifMs =  Date.now() -Date.parse(this.birthDate);
    var ageDate = new Date(ageDifMs);
    return Math.abs(ageDate.getUTCFullYear() - 1970);
  }

  public getTotalAccBal(): any {
    if (this.sumbal != null && this.sumbal != undefined)
      return this.sumbal;
    else
      return ClientRecord.NO_ACCOUNT_MESSAGE;
  }


  public getMaxAccBal() {
    if (this.maxbal != null && this.maxbal != undefined)
      return this.maxbal;
    else
      return ClientRecord.NO_ACCOUNT_MESSAGE;
  }


  public getMinAccBal() {
    if (this.minbal != null && this.minbal != undefined)
      return this.minbal;
    else
      return ClientRecord.NO_ACCOUNT_MESSAGE;
  }
}
