import {PhoneType} from "./PhoneType";

export class ClientPhone {
  public client:number;
  public type:PhoneType;
  public number:string;
  public name:string;

  constructor(){
    this.type=PhoneType.MOBILE;

  }
  public assign(o:any):ClientPhone{
    this.type=o.type;
    this.number=o.number;
    this.name=o.name;
    return this;
  }
}
