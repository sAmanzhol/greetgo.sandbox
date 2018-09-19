package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.FilterParams;
import java.util.List;

public interface ClientRegister {

  List<ClientRecord> getClients(FilterParams params);

  ClientRecord getClient(int id);

  void updateClientField(int id, String fieldName, Object fieldValue);

  void deleteClient(int id);

  void addClient(int id, String surname, String name, int actual);
}
