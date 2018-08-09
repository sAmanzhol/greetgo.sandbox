export class UserRecord {
  public id!: string;
  public fio!: string;
  public accountName!: string;
  public birthDate!: string;

  public static create(a: any): UserRecord {
    const ret = new UserRecord();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.id = a.id;
    this.fio = a.fio;
    this.accountName = a.accountName;
    this.birthDate = a.birthDate;
  }
}
