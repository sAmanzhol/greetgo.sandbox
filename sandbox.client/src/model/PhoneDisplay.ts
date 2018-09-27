export class PhoneDisplay {
  public id: number;
  public type: string;
  public value: number;

  public static create(a: any): PhoneDisplay {
    const ret = new PhoneDisplay();
    ret.assign(a);
    return ret;
  }

  constructor() {
    this.id = null;
    this.type = "Home";
    this.value = null;
  }

  assign(a: any) {
    this.id = a.id;
    this.type = a.type;
    this.value = a.value;
  }
}
