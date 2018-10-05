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

      int newClientId = createClient(clientToSave);
      createClientAddr(newClientId, clientToSave);
      createClientPhone(newClientId, clientToSave);

      return clientDao.get().getClientRecord(newClientId);
    } else {
      int clientId = Integer.parseInt(clientToSave.id);

      updateClient(clientId, clientToSave);
      updateClientAddr(clientId, clientToSave);
      updateClientPhone(clientId, clientToSave);

      return clientDao.get().getClientRecord(clientId);
    }
  }

  @Override
  public void delete(int id) {
    clientDao.get().deleteClient(id);
  }

  private int createClient(ClientToSave clientToSave) {
    Client client = new Client(clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.gender, clientToSave.birthDate, clientToSave.characterId);
    clientDao.get().insertClient(client);

    return clientDao.get().getId();
  }

  private void createClientAddr(int clientId, ClientToSave clientToSave) {
    Client_addr client_addr_reg = new Client_addr(clientId, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
    clientDao.get().insertClientAddr(client_addr_reg);

    Client_addr client_addr_fact = new Client_addr(clientId, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
    clientDao.get().insertClientAddr(client_addr_fact);
  }

  private void createClientPhone(int clientId, ClientToSave clientToSave) {
    for (int i = 0; i < clientToSave.numbers.size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbers.get(i);
      Client_phone client_phone = new Client_phone(clientId, phoneDisplay.type, phoneDisplay.number);
      clientDao.get().insertClientPhone(client_phone);
    }
  }

  private void updateClient(int clientId, ClientToSave clientToSave) {
    Client client = new Client(clientId, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.gender, clientToSave.birthDate, clientToSave.characterId);
    clientDao.get().updateClient(client);
  }

  private void updateClientPhone(int clientId, ClientToSave clientToSave) {
    for (int i = 0; i < clientToSave.numbersChange.get("created").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("created").get(i);
      Client_phone client_phone = new Client_phone(clientId, phoneDisplay.type, phoneDisplay.number);

      clientDao.get().insertClientPhone(client_phone);
    }

    for (int i = 0; i < clientToSave.numbersChange.get("updated").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("updated").get(i);
      Client_phone client_phone = new Client_phone(phoneDisplay.id, clientId, phoneDisplay.type, phoneDisplay.number);

      clientDao.get().updateClientPhone(client_phone);
    }

    for (int i = 0; i < clientToSave.numbersChange.get("deleted").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("deleted").get(i);
      clientDao.get().deleteClientPhone(phoneDisplay.id);
    }
  }

  private void updateClientAddr(int clientId, ClientToSave clientToSave) {
    Client_addr client_addr_reg = new Client_addr(clientId, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
    clientDao.get().updateClientAddr(client_addr_reg);

    Client_addr client_addr_fact = new Client_addr(clientId, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
    clientDao.get().updateClientAddr(client_addr_fact);
  }
}
