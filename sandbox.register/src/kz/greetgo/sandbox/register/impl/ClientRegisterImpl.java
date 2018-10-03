package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.impl.jdbc.ClientCountCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.ClientListCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;

import java.util.List;

// FIXME: 9/24/18 Избавься от варнингов в коде

@Bean
public class ClientRegisterImpl implements ClientRegister {
  public BeanGetter<ClientDao> clientDao;
  public BeanGetter<JdbcSandbox> jdbc;

  @Override
  public List<ClientRecord> list(ClientToFilter filter) {
    return jdbc.get().execute(new ClientListCallbackImpl(filter));
  }

  @Override
  public int count(ClientToFilter filter) {
    return jdbc.get().execute(new ClientCountCallbackImpl(filter));
  }

  @Override
  public ClientRecord crupdate(ClientToSave clientToSave) {
    return new ClientRecord();
  }

  @Override
  public ClientDisplay details(int id) {
//    return listDetails.stream()
//      .filter(client -> client.id == id)
//      .findFirst()
//      .orElse(new ClientDisplay());
    return new ClientDisplay();
  }

  @Override
  public void delete(int id) {
//    ClientDisplay clientToRemove = listDetails.stream()
//      .filter(client -> client.id == id)
//      .findFirst()
//      .orElse(new ClientDisplay());
//
//    list.remove(list.stream()
//      .filter(client -> client.id == id)
//      .findFirst()
//      .orElse(new ClientRecord()));
//
//    listDetails.remove(clientToRemove);
  }
}
