import {Address} from "./Address";
import {PhoneType} from "./PhoneType";


export class Phone {
  public id: number;
  public number:string;
  public type:PhoneType;


  constructor(TYPE) {
    this.number = "";
    this.type = PhoneType.HOME == TYPE ? PhoneType.HOME : PhoneType.MOBILE == TYPE ?  PhoneType.MOBILE  : PhoneType.WORK;
  }

  public static create(a : any): Phone {
    const ret = new Phone(a.type);
    ret.assign(a);
    return ret;
  }

  assign(a : any){
    this.id = a.id;
    this.type = PhoneType.HOME == a.type ? PhoneType.HOME : PhoneType.MOBILE ==  a.type ?  PhoneType.MOBILE  : PhoneType.WORK;
    this.number = a.number;
  }
}
