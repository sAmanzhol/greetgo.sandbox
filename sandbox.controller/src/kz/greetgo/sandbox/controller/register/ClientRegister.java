package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.report.ClientReportView;

import java.util.Date;
import java.util.List;

public interface ClientRegister {
  int count(ClientToFilter filter);

  List<ClientRecord> list(ClientToFilter filter);

  ClientRecord save(ClientToSave clientToSave);

  ClientDisplay details(int id);

  void delete(int id);

  void renderList(RenderFilter renderFilter) throws Exception;
}
