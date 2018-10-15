package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.controller.report.model.ClientReportFootData;
import kz.greetgo.sandbox.controller.report.model.ClientReportHeadData;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.dao_model.Client;
import kz.greetgo.sandbox.register.dao_model.ClientAddr;
import kz.greetgo.sandbox.register.dao_model.ClientPhone;
import kz.greetgo.sandbox.register.impl.jdbc.client.ClientCountCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.client.ClientListCallbackImpl;
import kz.greetgo.sandbox.register.impl.jdbc.client.ClientListRenderCallbackImpl;
import kz.greetgo.sandbox.register.util.JdbcSandbox;

import java.util.List;
import java.util.Objects;

@SuppressWarnings("WeakerAccess")
@Bean
public class ClientRegisterImpl implements ClientRegister {
  public BeanGetter<ClientDao> clientDao;
  public BeanGetter<JdbcSandbox> jdbc;

  @Override
  public void render(RenderFilter renderFilter) throws Exception {
    ClientReportHeadData head = new ClientReportHeadData();
    head.title = "Отчет:";

    renderFilter.view.start(head);

    jdbc.get().execute(new ClientListRenderCallbackImpl(renderFilter.clientToFilter, renderFilter.view));

    ClientReportFootData foot = new ClientReportFootData();
    foot.generatedBy = renderFilter.author;
    foot.generatedAt = renderFilter.createdAt;

    renderFilter.view.finish(foot);
  }

  @Override
  public List<ClientRecord> list(ClientToFilter filter) {
    return jdbc.get().execute(new ClientListCallbackImpl(filter));
  }

  @Override
  public int count(ClientToFilter filter) {
    return jdbc.get().execute(new ClientCountCallbackImpl(filter));
  }

  @Override
  public ClientDetails details(int id) {
    ClientDetails clientDetails = clientDao.get().details(id);
    clientDetails.numbers = clientDao.get().getClientPhones(clientDetails.id);

    return clientDetails;
  }

  @Override
  public ClientRecord save(ClientToSave clientToSave) {
    if (Objects.isNull(clientToSave.id)) {
      int newClientId = createClient(clientToSave);
      createClientAddr(newClientId, clientToSave);
      createClientPhone(newClientId, clientToSave);

      return clientDao.get().getClientRecord(newClientId);
    } else {
      int clientId = clientToSave.id;

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
    ClientAddr clientAddrReg = new ClientAddr(clientId, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
    clientDao.get().insertClientAddr(clientAddrReg);

    ClientAddr clientAddrFact = new ClientAddr(clientId, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
    clientDao.get().insertClientAddr(clientAddrFact);
  }

  private void createClientPhone(int clientId, ClientToSave clientToSave) {
    for (int i = 0; i < clientToSave.numbers.size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbers.get(i);
      ClientPhone clientPhone = new ClientPhone(clientId, phoneDisplay.type, phoneDisplay.number);
      clientDao.get().insertClientPhone(clientPhone);
    }
  }

  private void updateClient(int clientId, ClientToSave clientToSave) {
    Client client = new Client(clientId, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.gender, clientToSave.birthDate, clientToSave.characterId);
    clientDao.get().updateClient(client);
  }

  private void updateClientPhone(int clientId, ClientToSave clientToSave) {
    for (int i = 0; i < clientToSave.numbersChange.get("created").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("created").get(i);
      ClientPhone clientPhone = new ClientPhone(clientId, phoneDisplay.type, phoneDisplay.number);

      clientDao.get().insertClientPhone(clientPhone);
    }

    for (int i = 0; i < clientToSave.numbersChange.get("updated").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("updated").get(i);
      ClientPhone clientPhone = new ClientPhone(phoneDisplay.id, clientId, phoneDisplay.type, phoneDisplay.number);

      clientDao.get().updateClientPhone(clientPhone);
    }

    for (int i = 0; i < clientToSave.numbersChange.get("deleted").size(); i++) {
      PhoneDisplay phoneDisplay = clientToSave.numbersChange.get("deleted").get(i);
      clientDao.get().deleteClientPhone(phoneDisplay.id);
    }
  }

  private void updateClientAddr(int clientId, ClientToSave clientToSave) {
    ClientAddr clientAddrReg = new ClientAddr(clientId, "REG", clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration);
    clientDao.get().updateClientAddr(clientAddrReg);

    ClientAddr clientAddrFact = new ClientAddr(clientId, "FACT", clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence);
    clientDao.get().updateClientAddr(clientAddrFact);
  }
}
