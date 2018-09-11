import {PhoneType} from "./PhoneType";

export class ClientPhone {
  public type:PhoneType;
  public number:string;
  public name:string;

  constructor(name){
    this.number="+77";
    this.type=PhoneType.MOBILE;
    this.name=name;
  }
  public assign(o:any):ClientPhone{
    this.type=o.type;
    this.number=o.number;
    this.name=o.name;
    return this;
  }
}
