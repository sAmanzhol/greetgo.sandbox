import {GenderType} from "./GenderType";
import {ClientPhone} from "./ClientPhone";
import {ClientAddr} from "./ClientAddr";
import {Charm} from "./Charm";
import {AddrType} from "./AddrType";
import {ClientDetails} from "./ClientDetails";

export class ClientToSave {
  public id: number/*int*/|null;
  public firstname: string;
  public lastname: string;
  public patronymic: string;
  public gender: GenderType;
  public dateOfBirth: string;
  public characterId: number;
  public addressOfResidence: ClientAddr;
  public addressOfRegistration: ClientAddr;
  public phone: ClientPhone[]=[new ClientPhone()];

  constructor() {
    this.firstname = '';
    this.lastname = '';
    this.patronymic = '';
    this.gender = GenderType.MALE;
    this.dateOfBirth = '2018-08-22';
    this.characterId = new Charm().id;
    this.addressOfRegistration = new ClientAddr();
    this.addressOfRegistration.type = AddrType.REG;
    this.addressOfResidence = new ClientAddr();
    this.addressOfResidence.type = AddrType.FACT;
  }

  public assign(o: any): ClientDetails {
    if(o.id)
    this.id = o.id;
    this.firstname = o.firstname;
    this.lastname = o.lastname;
    this.patronymic = o.patronymic;
    this.gender = o.gender;
    this.dateOfBirth = o.dateOfBirth;
    this.characterId = o.characterId;
    if(o.addressOfRegistration){
      this.addressOfRegistration.street = o.addressOfRegistration.street;
      this.addressOfRegistration.house = o.addressOfRegistration.house;
      this.addressOfRegistration.flat = o.addressOfRegistration.flat;
      this.addressOfRegistration.type = AddrType.REG;}
    if(o.addressOfResidence){
      this.addressOfResidence.street = o.addressOfResidence.street;
      this.addressOfResidence.house = o.addressOfResidence.house;
      this.addressOfResidence.flat = o.addressOfResidence.flat;
      this.addressOfResidence.type = AddrType.FACT;}
    this.phone = o.phone;
    return this;
  }



}
