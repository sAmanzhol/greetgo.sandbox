export class UserInfo {
  public id!: string;
  public accountName!: string;
  public surname!: string;
  public name!: string;
  public patronymic!: string;
  public yellow!: boolean;

  public static create(a: any): UserInfo {
    const ret = new UserInfo();
    ret.assign(a);
    return ret;
  }

  public assign(a: any) {
    this.id = a.id;
    this.accountName = a.accountName;
    this.surname = a.surname;
    this.name = a.name;
    this.patronymic = a.patronymic;
    this.yellow = a.yellow;
  }
}
