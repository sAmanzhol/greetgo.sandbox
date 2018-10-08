export class PhoneDisplay {
  public id: number;
  public type: string;
  public number: string;

  public static create(a: any): PhoneDisplay {
    const ret = new PhoneDisplay();
    ret.assign(a);
    return ret;
  }

  constructor() {
    this.id = 0;
    this.type = "HOME";
    this.number = null;
  }

  assign(a: any) {
    this.id = a.id;
    this.type = a.type;
    this.number = a.number;
  }
}
