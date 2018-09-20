package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;

import java.util.List;

public interface ClientRegister {
  List<ClientRecord> list(String target, String type, String query);

  ClientDisplay crupdate(String id, ClientDisplay clientDisplay);

  ClientDisplay get(String id);

  ClientDisplay delete(String id);
}
