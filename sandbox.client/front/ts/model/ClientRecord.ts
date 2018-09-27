import {Charm} from "./Charm";

export class ClientRecord {
  public id:number/*int*/|null;
  public firstname:string|null;
  public lastname:string|null;
  public patronymic:string|null;
  public characterName:string;
  public dateOfBirth:Date = new Date();
  public totalAccountBalance:number/*int*/;
  public maximumBalance:number/*int*/;
  public minimumBalance:number/*int*/;

  constructor(){
    this.firstname="";
    this.lastname="";
    this.patronymic="";
    this.characterName=new Charm().name;
    this.dateOfBirth=new Date();
    this.totalAccountBalance=0;
    this.maximumBalance=0;
    this.minimumBalance=0;
  }

  public assign(o:any):ClientRecord{
    this.id=o.id;
    this.firstname=o.firstname;
    this.lastname=o.lastname;
    this.patronymic=o.patronymic;
    this.characterName=o.characterName;
    this.dateOfBirth=o.dateOfBirth;
    this.totalAccountBalance=o.totalAccountBalance;
    this.maximumBalance=o.maximumBalance;
    this.minimumBalance=o.minimumBalance;
    return this;
  }
}