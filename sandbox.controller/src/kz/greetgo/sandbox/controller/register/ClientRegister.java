package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.controller.report.ClientReportView;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;

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

  void generateReport(ClientToFilter clientToFilter, String author, Date createdAt, ClientReportView view) throws Exception;
}
