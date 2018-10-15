package kz.greetgo.sandbox.controller.model;

import java.util.Date;

@SuppressWarnings("unused")
public class RenderFilter {
  public ClientToFilter clientToFilter;
  public String author;
  public Date createdAt;

  public RenderFilter() {
  }

  public RenderFilter(ClientToFilter clientToFilter, String author, Date createdAt) {
    this.clientToFilter = clientToFilter;
    this.author = author;
    this.createdAt = createdAt;
  }
}
