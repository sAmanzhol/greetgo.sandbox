import {UserCan} from "@/model/UserCan";

export class PersonDisplay {
  public fio!: string;
  public username!: string;
  public role!: string | null;
  public cans!: UserCan[];

  public static create(a: any): PersonDisplay {
    const ret = new PersonDisplay();
    ret.assign(a);
    return ret;
  }

  public assign(a: any) {
    this.fio = a.fio;
    this.username = a.username;
    this.role = a.role;
  }
}