
import {GenderType} from "./GenderType";
import {ClientPhone} from "./ClientPhone";
import {ClientAddr} from "./ClientAddr";
import {Charm} from "./Charm";
import {AddrType} from "./AddrType";
import {PhoneType} from "./PhoneType";

export class ClientDetails {
  public id:number/*int*/;
  public firstname:string;
  public lastname:string;
  public patronymic:string;
  public gender:GenderType;
  public dateOfBirth:string;
  public character:Charm;
  public addressOfResidence: ClientAddr;
  public addressOfRegistration:ClientAddr;
  public phone : ClientPhone[];

  constructor(){
    this.id =0;
    this.firstname='';
    this.lastname='';
    this.patronymic='';
    this.dateOfBirth='';
    this.character=new Charm();

    this.addressOfRegistration=new ClientAddr();
    this.addressOfRegistration.type=AddrType.REG;
    this.addressOfResidence = new ClientAddr();
    this.addressOfResidence.type=AddrType.FACT;
    this.phone=[(new ClientPhone())];
  }
  public assign(o:any):ClientDetails{
    this.id =o.id;
    this.firstname=o.firstname;
    this.lastname=o.lastname;
    this.patronymic=o.patronymic;
    this.dateOfBirth=o.dateOfBirth;
    this.character=o.character;
    this.addressOfRegistration.street= o.addressOfRegistration.street;
    this.addressOfRegistration.house= o.addressOfRegistration.house;
    this.addressOfRegistration.flat= o.addressOfRegistration.flat;
    this.addressOfRegistration.type=AddrType.REG;
    this.addressOfResidence.street= o.addressOfRegistration.street;
    this.addressOfResidence.house = o.addressOfRegistration.house;
    this.addressOfResidence.flat = o.addressOfRegistration.flat;
    this.addressOfResidence.type=AddrType.FACT;

    return this;
  }
}
