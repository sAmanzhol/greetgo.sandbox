package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;

import java.util.List;

public interface ClientRegister {
  List<ClientRecord> list(ClientToFilter filter);

  int count(ClientToFilter filter);

  // FIXME: 9/24/18 Должен возвращать ClientRecord. Переименуй метод в save
  ClientDisplay crupdate(String id, ClientToSave clientDisplay);

  // FIXME: 9/24/18 Название метода должно бытьdetails
  ClientDisplay get(String id);

  // FIXME: 9/24/18 Не должен возвращать ничего
  ClientDisplay delete(String id);
}
