export class ClientRecord {
  public id:number/*int*/;
  public firstName:string;
  public lastName:string;
  public patronymic:string;
  public character:string;
  public dateOfBirth:string;
  public totalAccountBalance:number/*int*/;
  public maximumBalance:number/*int*/;
  public minimumBalance:number/*int*/;

  constructor(){
    this.firstName="";
    this.lastName="";
    this.patronymic="";
    this.character="";
    this.dateOfBirth="";
    this.totalAccountBalance=0;
    this.maximumBalance=0;
    this.minimumBalance=0;
  }
}