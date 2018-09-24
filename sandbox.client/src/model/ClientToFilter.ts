export class ClientToFilter {
  public target: string;
  public type: string;
  public query: string;
  public page: string;
  public itemCount: string;

  public static createDefault(): ClientToFilter {
    return ClientToFilter.create({
      target: "id",
      type: "asc",
      query: "",
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
    this.target = a.target;
    this.type = a.type;
    this.query = a.query;
    this.page = a.page;
    this.itemCount = a.itemCount;
  }
}
