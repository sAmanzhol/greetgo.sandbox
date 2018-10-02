import {GenderType} from "./GenderType";
import {Charm} from "./Charm";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {ClientItem} from "../app/client-table/client-table.component";
import {forEach} from "@angular/router/src/utils/collection";
import {Account} from "./Account";

export class ClientRecord {
  public id: number;
  public surname: string;
  public name: string;
  public patronomic: string;
  public birthDate:string;
  public gender: GenderType;
  public charm: Charm;
  public addressP:Address;
  public addressF:Address;
  public phoneH:Phone;
  public phoneW:Phone;
  public phoneM:Phone[];
  public account:Account[];

  public static create(a : any): ClientRecord {
    const ret = new ClientRecord();
    ret.assign(a);
    return ret;
  }

  public assign(a : any){
    this.id = a.id;
    this.surname = a.surname;
    this.name = a.name;
    this.patronomic = a.patronomic;
    this.birthDate = a.birthDate;
    this.gender = a.gender;
    this.charm = a.charm;
    this.addressP = a.addressP;
    this.addressF = a.addressF;
    this.phoneH = a.phoneH;
    this.phoneW = a.phoneW;
    this.phoneM = a.phoneM;
    this.account = a.account;
  }

  public static createEmpty() : ClientRecord{
    let empty  = new ClientRecord();
    empty.id = undefined;
    empty.surname = "";
    empty.name = "";
    empty.patronomic = "";
    empty.birthDate = "";
    empty.gender = "";
    empty.charm = new Charm();
    empty.addressP = new Address();
    empty.addressF = new Address();
    empty.phoneH = new Phone();
    empty.phoneW = new Phone();
    empty.phoneM = [];
    empty.account = [];

    return empty;
  }

  public getAge(){

  }

  public getTotalAccBal() : any{
    let total = 0;
    if(this.account != null && this.account.length != 0)
      {
         this.account.forEach((acc) => {
           total += acc.money;
         });
         return total;
      }
      else
        return "Нет счета";
  }


  public getMaxAccBal(){
    let max = 0;
    if(this.account != null && this.account.length != 0)
    {
      max = this.account.pop().money;
      this.account.forEach((acc) => {
        if(max < acc.money )
          max = acc.money
      });
      return max;
    }
    else
      return "Нет счета";
  }


  public getMinAccBal(){
    let min = 0;
    if(this.account != null && this.account.length != 0)
    {
      min = this.account.pop().money;
      this.account.forEach((acc) => {
        if(min > acc.money )
          min = acc.money
      });
      return min;
    }
    else
      return "Нет счета";
  }
}
