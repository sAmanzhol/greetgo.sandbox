import {AddressOfResidence} from "./AddressOfResidence";
import {AddressOfRegistration} from "./AddressOfRegistration";
import {Phone} from "./Phone";

export class Client {
  public id:number/*int*/;
  public firstname:string;
  public lastname:string;
  public patronymic:string;
  public gender:string;
  public dateOfBirth:string;
  public character:string;
  public addressOfResidence: AddressOfResidence;
  public addressOfRegistration:AddressOfRegistration;
  public phone :Phone;

  constructor(){
    this.id =0;
    this.firstname='';
    this.lastname='';
    this.patronymic='';
    this.gender='';
    this.dateOfBirth='';
    this.character='';
    this.addressOfRegistration=new AddressOfRegistration();
    this.addressOfResidence = new AddressOfResidence();
    this.phone = new Phone()
  }


}




