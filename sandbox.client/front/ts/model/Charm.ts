export class Charm {
    public id: number/*int*/;
    public name: string;
    public description: string;
    public energy: number/*int*/;

    constructor() {
        this.id = 0;
        this.name = '';
        this.description = '';
        this.energy = 0;
    }

    public assign(o: any): Charm {
        this.id = o.id;
        this.name = o.name
        this.description = o.description
        this.energy = o.energy
        return this;
    }

}