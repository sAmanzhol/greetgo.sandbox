import {AddrType} from "./AddrType";

export class ClientAddr {

    public client:number;
    public type: AddrType;
    public street: string;
    public house: string;
    public flat: string;

    constructor() {
        this.street = '';
        this.house = "";
        this.flat = '';
    }

    assign(o: any): ClientAddr {
        this.type = o.type;
        this.street = o.street;
        this.house = o.house;
        this.flat = o.flat;
        return this;
    }

}