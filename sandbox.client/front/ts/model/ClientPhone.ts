import {PhoneType} from "./PhoneType";

export class ClientPhone {
  public type:PhoneType;
  public number:string;

  constructor(){
    this.number='';
    this.type=PhoneType.WORK
  }
}
