import {PhoneType} from "./PhoneType";

export class ClientPhone {
  public type:PhoneType;
  public number:string;

  constructor(){
    this.number='+7 7';
    this.type=PhoneType.MOBILE

  }
  public assign(o:any):ClientPhone{
    this.type=o.type;
    this.number=o.number;
    return this;
  }
}
