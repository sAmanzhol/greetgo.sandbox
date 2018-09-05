import {AddressOfResidence} from "./AddressOfResidence";
import {AddressOfRegistration} from "./AddressOfRegistration";
import {Phone} from "./Phone";
import {PhoneType} from "./PhoneType";

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
    // TODO: asset 9/4/18 ne nuzhno plodirovat class AddressOfRegistration i AddressOfResidence.
    // TODO: Mozho izpolzovat tipa odin class ClientAddress i vnutri opredelit cherez enum chto eto fact ili reg address
    this.addressOfRegistration=new AddressOfRegistration();
    this.addressOfResidence = new AddressOfResidence();
    // TODO: asset 9/4/18 i v phone
    this.phone= new Phone();
  }

}





