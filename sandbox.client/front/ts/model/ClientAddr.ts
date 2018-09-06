import {AddrType} from "./AddrType";

export class ClientAddr {
  public type: AddrType;
  public street:string;
  public house:string;
  public flat:string;

  constructor(){
    this.street='';
    this.house="";
    this.flat='';
  }
}