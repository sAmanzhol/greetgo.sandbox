export class ClientRecord {
  public id: number;
  public fio: string;
  public character: string;
  public age: number;
  public balance: number;
  public balanceMax: number;
  public balanceMin: number;

  public static create(a: any): ClientRecord {
    const ret = new ClientRecord();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.id = a.id;
    this.fio = a.fio;
    this.character = a.character;
    this.age = a.age;
    this.balance = a.balance;
    this.balanceMax = a.balanceMax;
    this.balanceMin = a.balanceMin;
  }
}
