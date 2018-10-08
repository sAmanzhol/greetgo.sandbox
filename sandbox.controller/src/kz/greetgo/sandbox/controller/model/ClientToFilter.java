package kz.greetgo.sandbox.controller.model;

import java.util.Date;

public class ClientToFilter {
  // FIXME: 9/24/18 Название target не говорит для чего будет использоваться переменная. Может быть больше подойдет sortColumn
  public SortColumn sortColumn;
  // FIXME: 9/24/18 то же самое как и с target. Возможно, что-то вроде sortDirection подойдет лучше
  public SortDirection sortDirection;
  // FIXME: 9/24/18 просто fio
  public String fio;
  public int page;
  public int itemCount;

  public String author;

  public ClientToFilter() {
  }

  public ClientToFilter(String sortColumn, String sortDirection, String fio, int page, int itemCount) {
    this.sortColumn = SortColumn.valueOf(sortColumn);
    this.sortDirection = SortDirection.valueOf(sortDirection);
    this.fio = fio;
    this.page = page;
    this.itemCount = itemCount;
  }

  public enum SortColumn {
    id,
    fio,
    age,
    balance,
    balanceMax,
    balanceMin
  }

  public enum SortDirection {
    ASC,
    DESC
  }
}