import {GenderType} from "./GenderType";
import {Charm} from "./Charm";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {ClientItem} from "../app/client-table/client-table.component";

export class ClientRecord {
  public id: number;
  public surname: string;
  public name: string;
  public patronomic: string;
  public birthDate:string;
  public gender: GenderType;
  public charm: Charm;
  public adresss:Address;
  public phone:Phone;
  public account:Account[];

  public static create(a : any): ClientRecord {
    const ret = new ClientRecord();
    ret.assign(a);
    return ret;
  }

  assign(a : any){
    this.id = a.id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronomic = a.patronomic;
    this.birthDate = a.birthDate;
    this.gender = a.gender;
    this.charm = a.charm;
    this.adresss = a.adresss;
    this.phone = a.phone;
    this.account = a.account;
  }

  public getAge(){
    //
  }

  public getTotalAccBal(){

  }


  public getMaxAccBal(){

  }


  public getMinAccBal(){

  }
}
