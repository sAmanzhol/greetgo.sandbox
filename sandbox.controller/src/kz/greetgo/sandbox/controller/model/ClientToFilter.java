package kz.greetgo.sandbox.controller.model;

import java.util.Date;

public class ClientToFilter {
  public SortColumn sortColumn;
  public SortDirection sortDirection;
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