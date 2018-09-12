import {UserInfo} from "./UserInfo";

export class Charm {
    public id: number/*int*/;
    public name: string;
    public description: string;
    public energy: number/*int*/;
    public actually:boolean;
    constructor() {
        this.id = 0;
        this.name = '';
        this.description = '';
        this.energy = 0;
        this.actually=false;
    }

    public assign(o: any): Charm {
        this.id = o.id;
        this.name = o.name;
        this.description = o.description;
        this.energy = o.energy;
        this.actually=o.actually;
        return this;
    }

    public static copy(a:any):Charm{
        let ret = new Charm();
        ret.assign(a);
        return ret;
}

}