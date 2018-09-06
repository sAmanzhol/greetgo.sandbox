import {CharacterType} from "./CharacterType";
/**
 * Created by msultanova on 9/4/18.
 */
export class ClientRecord{
  public fio: string;
  public character: CharacterType;
  public age: number/*int*/;
  public totalBalance: number/*int*/;
  public minBalance: number/*int*/;
  public maxBalance: number/*int*/;
  public clientId: number/*long*/;

  public static create(a: any): ClientRecord {
    const ret = new ClientRecord();
    ret.assign(a);
    return ret;
  }
  assign(a: any) {
    this.age = a.age;
    this.fio = a.fio;
    this.character = a.character;
    this.maxBalance = a.maxBalance;
    this.totalBalance = a.totalBalance;
    this.minBalance = a.minBalance;
  }
}
