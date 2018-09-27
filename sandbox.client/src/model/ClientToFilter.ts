export class ClientToFilter {
  public sortColumn: string;
  public sortDirection: string;
  public fio: string;
  public page: number;
  public itemCount: number;

  public static createDefault(): ClientToFilter {
    return ClientToFilter.create({
      sortColumn: "id",
      sortDirection: "asc",
      fio: "",
      page: 1,
      itemCount: 10
    });
  }

  public static create(a: any): ClientToFilter {
    const ret = new ClientToFilter();
    ret.assign(a);
    return ret;
  }

  assign(a: any) {
    this.sortColumn = a.sortColumn;
    this.sortDirection = a.sortDirection;
    this.fio = a.fio;
    this.page = a.page;
    this.itemCount = a.itemCount;
  }
}
