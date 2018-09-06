export class ClientRecord {
  public id:number/*int*/;
  public firstname:string|null;
  public lastname:string|null;
  public patronymic:string|null;
  public character:string;
  public dateOfBirth:string;
  public totalAccountBalance:number/*int*/;
  public maximumBalance:number/*int*/;
  public minimumBalance:number/*int*/;

  constructor(){
    this.firstname="";
    this.lastname="";
    this.patronymic="";
    this.character="";
    this.dateOfBirth="";
    this.totalAccountBalance=0;
    this.maximumBalance=0;
    this.minimumBalance=0;
  }

  public assign(o:any):ClientRecord{
    this.firstname=o.firstname;
    this.lastname=o.lastname;
    this.patronymic=o.patronymic;
    this.character=o.character;
    this.dateOfBirth=o.dateOfBirth;
    this.totalAccountBalance=o.totalAccountBalance;
    this.maximumBalance=o.maximumBalance;
    this.minimumBalance=o.minimumBalance;
    return this;
  }
}