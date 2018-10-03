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

//    if (clientToSave.id != null) {
//      ClientDisplay clientDisplay = new ClientDisplay(clientToSave.id, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.birthDate, clientToSave.gender, clientToSave.characterId, clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration, clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence, clientToSave.numbers);
//
//      ClientDisplay clientToUpdate = listDetails.stream()
//        .filter(client -> client.id == clientToSave.id)
//        .findFirst()
//        .orElse(new ClientDisplay());
//
//      int indexToUpdate = listDetails.indexOf(clientToUpdate);
//
//      listDetails.set(indexToUpdate, clientDisplay);
//
//      calendar.setTime(clientDisplay.birthDate);
//      LocalDate birthdate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
//      LocalDate now = LocalDate.now();
//      int age = Period.between(birthdate, now).getYears();
//
//      ClientRecord clientRecordToUpdate = list.stream()
//        .filter(client -> client.id == clientToSave.id)
//        .findFirst()
//        .orElse(new ClientRecord());
//
//      int indexRecordToUpdate = list.indexOf(clientRecordToUpdate);
//
//      CharacterRecord character = listCharacters.stream()
//        .filter(ch -> ch.id == clientDisplay.characterId)
//        .findFirst()
//        .orElse(new CharacterRecord());
//
//      ClientRecord clientRecord = new ClientRecord(
//        clientRecordToUpdate.id, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.name, age, 0, 1000, 0
//      );
//
//      list.set(indexRecordToUpdate, clientRecord);
//
//      return clientRecord;
//    } else {
//      int newId = list.get(list.size() - 1).id + 1;
//      ClientDisplay clientDisplay = new ClientDisplay(newId, clientToSave.surname, clientToSave.name, clientToSave.patronymic, clientToSave.birthDate, clientToSave.gender, clientToSave.characterId, clientToSave.streetRegistration, clientToSave.houseRegistration, clientToSave.apartmentRegistration, clientToSave.streetResidence, clientToSave.houseResidence, clientToSave.apartmentResidence, clientToSave.numbers);
//
//      listDetails.add(clientDisplay);
//
//      calendar.setTime(clientDisplay.birthDate);
//      LocalDate birthdate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
//      LocalDate now = LocalDate.now();
//      int age = Period.between(birthdate, now).getYears();
//
//      CharacterRecord character = listCharacters.stream()
//        .filter(ch -> ch.id == clientDisplay.characterId)
//        .findFirst()
//        .orElse(new CharacterRecord());
//
//      ClientRecord clientRecord = new ClientRecord(
//        newId, clientDisplay.surname + " " + clientDisplay.name + " " + clientDisplay.patronymic, character.name, age, 0, 1000, 0
//      );
//
//      list.add(clientRecord);
//
//      return clientRecord;
//    }

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
