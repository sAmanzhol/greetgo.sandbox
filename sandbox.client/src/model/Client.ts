import {CharacterType} from "./CharacterType";
import {Address} from "./Address";
import {Phone} from "./Phone";
/**
 * Created by msultanova on 9/6/18.
 */
export class Client{
  public id: number/*long*/;
  public surname: string;
  public name: string;
  public patronymic: string;
  public character: CharacterType;
  public age: number/*int*/;
  public totalBalance: number/*int*/;
  public minBalance: number/*int*/;
  public maxBalance: number/*int*/;
  public actualAddress: Address;
  public registrationAddress: Address;
  public phones: Phone[];
}

