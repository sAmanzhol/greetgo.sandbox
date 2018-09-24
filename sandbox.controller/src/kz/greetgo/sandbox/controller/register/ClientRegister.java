package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;

import java.util.List;

public interface ClientRegister {
  List<ClientRecord> list(ClientToFilter filter);

  int count(ClientToFilter filter);

  ClientDisplay crupdate(String id, ClientToSave clientDisplay);

  ClientDisplay get(String id);

  ClientDisplay delete(String id);
}
