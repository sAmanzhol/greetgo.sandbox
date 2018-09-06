import {UserInfo} from "./UserInfo";

export class ClientFilter {
  public firstname:string;
  public lastname:string;
  public patronymic:string;
  public orderBy:string;
  public sort:boolean;
  public offSet:number/*int*/;

  public assign(o: any): ClientFilter{
    this.firstname= o.firstname;
    this.lastname = o.lastname;
    this.patronymic = o.patronymic;
    this.orderBy = o.orderBy;
    this.sort = o.sort;
    this.offSet = o.offSet;
    return this;
  }
  constructor(){
    this.firstname= "";
    this.lastname = "";
    this.patronymic = "";
    this.orderBy = "";
    this.sort = true;
    this.offSet = 0;

  }
}
