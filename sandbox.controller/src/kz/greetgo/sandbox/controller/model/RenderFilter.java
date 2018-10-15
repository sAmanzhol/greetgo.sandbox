package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.report.ClientReportView;

import java.util.Date;

@SuppressWarnings("unused")
public class RenderFilter {
  public ClientToFilter clientToFilter;
  public String author;
  public Date createdAt;

  // FIXME: 10/15/18 вьюшка не относиться к фильтру, не должна быть в этом классе
  public ClientReportView view;

  public RenderFilter() {

  }

  public RenderFilter(ClientToFilter clientToFilter, String author, Date createdAt, ClientReportView view) {
    this.clientToFilter = clientToFilter;
    this.author = author;
    this.createdAt = createdAt;
    this.view = view;
  }
}
