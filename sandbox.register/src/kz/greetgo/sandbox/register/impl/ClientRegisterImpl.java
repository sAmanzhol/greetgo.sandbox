package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.dao_model.Client;
import kz.greetgo.sandbox.register.dao_model.Client_addr;
import kz.greetgo.sandbox.register.dao_model.Client_phone;
import kz.greetgo.sandbox.register.impl.jdbc.ClientCountCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.ClientListCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;
import kz.greetgo.util.RND;

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
  public ClientDisplay details(int id) {
    ClientDisplay clientDisplay = clientDao.get().details(id);
    clientDisplay.numbers = clientDao.get().getClientPhones(clientDisplay.id);

    return clientDisplay;
  }

  @Override
  public ClientRecord save(ClientToSave clientToSave) {
    if (clientToSave.id.trim().isEmpty() || clientToSave.id.equalsIgnoreCase("")) {
      // TODO: 10/4/18 sequence

      Client client = new Client(clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.gender, clientToSave.birthDate, clientToSave.characterId);
      clientDao.get().insertClient(client);

      int newId = clientDao.get().getId();

      System.out.println(newId);

      Client_addr client_addr_reg = new Client_addr(newId, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
      clientDao.get().insertClientAddr(client_addr_reg);

      Client_addr client_addr_fact = new Client_addr(newId, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
      clientDao.get().insertClientAddr(client_addr_fact);

      for (int i = 0; i < clientToSave.numbers.size(); i++) {
        PhoneDisplay phoneDisplay = clientToSave.numbers.get(i);
        Client_phone client_phone = new Client_phone(newId, phoneDisplay.type, phoneDisplay.number);
        clientDao.get().insertClientPhone(client_phone);
      }

      return clientDao.get().getClientRecord(newId);
    } else {
      int id = Integer.parseInt(clientToSave.id);

      Client client = new Client(id, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.gender, clientToSave.birthDate, clientToSave.characterId);
      clientDao.get().updateClient(client);

      Client_addr client_addr_reg = new Client_addr(id, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
      clientDao.get().updateClientAddr(client_addr_reg);

      Client_addr client_addr_fact = new Client_addr(id, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
      clientDao.get().updateClientAddr(client_addr_fact);

      for (int i = 0; i < clientToSave.numbers.size(); i++) {
        PhoneDisplay phoneDisplay = clientToSave.numbers.get(i);
        Client_phone client_phone = new Client_phone(phoneDisplay.id, id, phoneDisplay.type, phoneDisplay.number);

        clientDao.get().insertClientPhone(client_phone);
      }

      return clientDao.get().getClientRecord(id);
    }
  }

  @Override
  public void delete(int id) {
    clientDao.get().delete(id);
  }
}
