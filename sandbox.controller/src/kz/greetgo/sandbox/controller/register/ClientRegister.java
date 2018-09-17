package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.FilterParams;
import kz.greetgo.sandbox.controller.model.PersonDisplay;
import kz.greetgo.sandbox.controller.model.SessionHolder;
import kz.greetgo.security.session.SessionIdentity;

import java.util.List;

public interface ClientRegister {

  List<ClientRecord> getClients(FilterParams params);

}
