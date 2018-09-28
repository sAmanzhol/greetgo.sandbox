package kz.greetgo.sandbox.controller.model;

public class ClientToFilter {
  // FIXME: 9/24/18 Название target не говорит для чего будет использоваться переменная. Может быть больше подойдет sortColumn
  public String sortColumn;
  // FIXME: 9/24/18 то же самое как и с target. Возможно, что-то вроде sortDirection подойдет лучше
  public String sortDirection;
  // FIXME: 9/24/18 просто fio
  public String fio;
  public int page;
  public int itemCount;

  public ClientToFilter() {
  }

  public ClientToFilter(String sortColumn, String sortDirection, String fio, int page, int itemCount) {
    this.sortColumn = sortColumn;
    this.sortDirection = sortDirection;
    this.fio = fio;
    this.page = page;
    this.itemCount = itemCount;
  }
}