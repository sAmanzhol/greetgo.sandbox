import {ClientRecord} from "./ClientRecord";

export class Charm{

  public id : number;
  public name : string;
  public description: string;
  public energy:number;

  public static create(a : any): Charm {
    const ret = new Charm();
    ret.assign(a);
    return ret;
  }

  assign(a : any){
    this.id = a.id;
    this.name = a.name;
    this.description = a.description;
    this.energy = a.energy;
  }

}
