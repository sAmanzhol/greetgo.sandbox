package kz.greetgo.sandbox.controller.model;

public class ClientToFilter {
  // FIXME: 9/24/18 Название target не говорит для чего будет использоваться переменная. Может быть больше подойдет sortColumn
  public String target;
  // FIXME: 9/24/18 то же самое как и с target. Возможно, что-то вроде sortDirection подойдет лучше
  public String type;
  // FIXME: 9/24/18 просто fio
  public String query;
  public String page;
  public String itemCount;

  public ClientToFilter() {
  }

  public ClientToFilter(String target, String type, String query, String page, String itemCount) {
    this.target = target;
    this.type = type;
    this.query = query;
    this.page = page;
    this.itemCount = itemCount;
  }
}