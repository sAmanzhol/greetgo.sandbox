export class PhoneDisplay {
  public type: string;
  public value: number;

  public static create(a: any): PhoneDisplay {
    const ret = new PhoneDisplay();
    ret.assign(a);
    return ret;
  }

  constructor() {
    this.type = "Home";
    this.value = null;
  }

  assign(a: any) {
    this.type = a.type;
    this.value = a.value;
  }
}
