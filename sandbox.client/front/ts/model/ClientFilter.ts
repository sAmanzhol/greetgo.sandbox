export class ClientFilter {
    public firstname: string;
    public lastname: string;
    public patronymic: string;
    public orderBy: string;
    public sort: boolean;
    public page: number/*int*/;
    public pageTotal: number/*int*/;
    public recordSize: number/*int*/;
    public recordTotal: number/*int*/;

    constructor() {
        this.firstname = "";
        this.lastname = "";
        this.patronymic = "";
        this.orderBy = "";
        this.sort = true;
        this.page = 0;
      this.recordTotal = 0;
      this.recordSize = 20;
      this.pageTotal = 1

    }
    public assign(o: any): ClientFilter {
        this.firstname = o.firstname;
        this.lastname = o.lastname;
        this.patronymic = o.patronymic;
        this.orderBy = o.orderBy;
        this.sort = o.sort;
        this.page = o.page;
        this.pageTotal = o.pageTotal;
        this.recordSize = o.recordSize;
        this.recordTotal = o.recordTotal;
        return this;
    }
}
