package kz.greetgo.sandbox.controller.model;

public class ClientToFilter {
  public String target;
  public String type;
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