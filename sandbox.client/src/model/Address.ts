import {Account} from "./Account";
import {AddressType} from "./AddressType";

export class Address {
  public id : number;
  public client: number;
  public type:AddressType;
  public street:string;
  public house:string;
  public flat:string;

  constructor(TYPE) {
    this.type = AddressType.REG == TYPE?AddressType.REG:AddressType.FACT;
    this.street = "";
    this.house = "";
    this.flat = "";
  }

  public static create(a : any): Address {
    const ret = new Address(a.type);
    ret.assign(a);
    return ret;
  }

  assign(a : any){
    this.id = a.id;
    this.type = AddressType.REG == a.type?AddressType.REG:AddressType.FACT;
    this.flat = a.flat;
    this.street = a.street;
    this.house = a.house;
    this.client = a.client;
  }
}
