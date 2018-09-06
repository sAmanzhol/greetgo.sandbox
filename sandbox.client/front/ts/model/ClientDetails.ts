
import {GenderType} from "./GenderType";
import {ClientPhone} from "./ClientPhone";
import {ClientAddr} from "./ClientAddr";
import {Charm} from "./Charm";
import {AddrType} from "./AddrType";

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
  public phone : Array<ClientPhone>;

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
    this.phone= new Array<ClientPhone>();
  }
}
