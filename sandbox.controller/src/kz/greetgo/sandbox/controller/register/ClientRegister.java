package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.report.ClientReportView;

import java.util.Date;
import java.util.List;

public interface ClientRegister {
  int count(ClientToFilter filter);

  List<ClientRecord> list(ClientToFilter filter);

  // FIXME: 9/24/18 Должен возвращать ClientRecord. Переименуй метод в save
  ClientRecord save(ClientToSave clientToSave);

  // FIXME: 9/24/18 Название метода должно бытьdetails
  ClientDisplay details(int id);

  // FIXME: 9/24/18 Не должен возвращать ничего
  void delete(int id);

  void renderList(RenderFilter renderFilter) throws Exception;
}
