import {CharacterType} from "./CharacterType";
import {Address} from "./Address";
import {Phone} from "./Phone";
/**
 * Created by msultanova on 9/5/18.
 */
export class ClientDetail{

  public surname: string;
  public name: string;
  public patronymic: string;
  public character: CharacterType;
  public actualAddress: Address;
  public registrationAddress: Address;
  public phones: Phone[];
  public clientId: number/*long*/;
}
