export class Account{
  public id : number;
  public client_id : number;
  public money : number;
  public number : string;
  public registered_at: string;


  public static create(a : any): Account {
    const ret = new Account();
    ret.assign(a);
    return ret;
  }

  assign(a : any){
    this.id = a.id;
    this.client_id = a.client_id;
    this.money = a.money;
    this.number =  a.number;
    this.registered_at = a.registered_at;
  }
}
