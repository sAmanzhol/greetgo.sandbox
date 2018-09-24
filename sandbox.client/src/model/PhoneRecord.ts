export class PhoneRecord {
  public id: string;
  public type: string;

  public static create(a: any): PhoneRecord {
    const ret = new PhoneRecord();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.id = a.id;
    this.type = a.type;
  }
}
