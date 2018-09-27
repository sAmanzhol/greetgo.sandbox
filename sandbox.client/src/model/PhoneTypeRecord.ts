export class PhoneTypeRecord {
  public id: number;
  public type: string;

  public static create(a: any): PhoneTypeRecord {
    const ret = new PhoneTypeRecord();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.id = a.id;
    this.type = a.type;
  }
}
