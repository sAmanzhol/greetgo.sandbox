export class PhoneDisplay {
  public id: string;
  public type: string;
  public value: string;

  public static create(a: any): PhoneDisplay {
    const ret = new PhoneDisplay();
    ret.assign(a);
    return ret;
  }

  constructor() {
    this.id = "";
    this.type = "Home";
    this.value = ""
  }

  assign(a: any) {
    this.id = a.id;
    this.type = a.type;
    this.value = a.value;
  }
}
