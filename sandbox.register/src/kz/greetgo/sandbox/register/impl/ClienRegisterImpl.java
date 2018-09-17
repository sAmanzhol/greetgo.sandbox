package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by msultanova on 9/4/18.
 */
@Bean
public class ClienRegisterImpl implements ClientRegister {

  List<Client> clients = null;
  List<ClientRecord> clientRecords = null;
  List<Character> characters = null;
  List<Gender> genders = null;
  List<PhoneDetail> phoneDetails = null;


  {
    createClients();
  }

  public void createClients() {

    clients = new ArrayList<>();

    phoneDetails = new ArrayList<PhoneDetail>();
    PhoneDetail phoneDetail1 = new PhoneDetail(PhoneType.MOBILE, "Мобильный");
    PhoneDetail phoneDetail2 = new PhoneDetail(PhoneType.HOME, "Домашний");
    PhoneDetail phoneDetail3 = new PhoneDetail(PhoneType.WORK, "Рабочий");
    phoneDetails.add(phoneDetail1);
    phoneDetails.add(phoneDetail2);
    phoneDetails.add(phoneDetail3);


    List<Phone> phones = new ArrayList<>();
    phones.add(new Phone(phoneDetail1, "7474938358"));
    phones.add(new Phone(phoneDetail2, "7273810983"));

    genders = new ArrayList<Gender>();
    Gender male = new Gender(GenderType.MALE, "мужской");
    Gender female = new Gender(GenderType.FEMALE, "женский");
    genders.add(male);
    genders.add(female);

    characters = new ArrayList<>();
    Character opennesCharacter = new Character(CharacterType.OPENNESS, "открытый");
    Character agreeablenessCharacter = new Character(CharacterType.AGREEABLENESS, "любзеный");
    Character conscientiousnessCharacter = new Character(CharacterType.CONSCIENTIOUSNESS, "добросовестный");
    Character extraversionCharacter = new Character(CharacterType.EXTRAVERSION, "экстраверт");
    Character neuroticismCharacter = new Character(CharacterType.NEUROTICISM, "невротичный");

    characters.add(opennesCharacter);
    characters.add(agreeablenessCharacter);
    characters.add(conscientiousnessCharacter);
    characters.add(extraversionCharacter);
    characters.add(neuroticismCharacter);

    Date birthdayDate = new Date();

    try {
      birthdayDate = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", female, birthdayDate, opennesCharacter, 20, 1000, 475, 5000, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(2, "Kas", "Gvlv", "osoos", male, birthdayDate, agreeablenessCharacter, 7, 100, 0, 1425, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(3, "Vsis", "Akkdd", "llsls", female, birthdayDate, extraversionCharacter, 75, 10, 8, 5545, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(4, "ASlx", "Bodd", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 40, 12, 5478, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(5, "KJJlxl", "KJJlxl", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 100, 74, 6574, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(6, "Cwqwd", "AXC", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 10, 475, 96, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(7, "Ruc", "Ruc", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 5, 252, 78, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(8, "Mnid", "Mnid", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 250, 77, 68, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(6, "Titr", "Titr", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 2370, 56, 15, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(7, "Vif", "Vif", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 124, 74, 23, Address.empty(), new Address("Mamyr-4", "311", 38), phones));
    clients.add(new Client(8, "Dan", "Dan", "lslslsl", male, birthdayDate, neuroticismCharacter, 1, 7, 825, 57, Address.empty(), new Address("Mamyr-4", "311", 38), phones));

  }

  @Override
  public List<ClientRecord> getClientList() {
    clientRecords = new ArrayList<>();
    //createClients();
    if (clients != null) {
      for (Client client : clients) {
        //ClientRecord tempClientRecord = createRecordFromClient(client);
        clientRecords.add(convertClientToRecord(client));
      }
    }
    return clientRecords;
  }

  public ClientRecord convertClientToRecord(Client client) {
    ClientRecord clientRecord = new ClientRecord();
    clientRecord.fio = client.surname + " " + client.name + " " + client.patronymic;
    clientRecord.age = client.age;
    clientRecord.totalBalance = client.totalBalance;
    clientRecord.minBalance = client.minBalance;
    clientRecord.maxBalance = client.maxBalance;
    clientRecord.character = client.character;
    clientRecord.clientId = client.id;
    //clientRecord.phones = client.phones;
    return clientRecord;
  }

  public Client convertToSaveToClient(ClientToSave toSave) {
    Client client = new Client();
    if(toSave.clientID > 0) {
      client.id = toSave.clientID;
    } else {
      client.id = clients.get(clients.size() - 1).id + 1;
    }
    client.name = toSave.name;
    client.surname = toSave.surname;
    client.patronymic = toSave.patronymic;
    client.actualAddress = toSave.actualAddress;
    client.age = toSave.age;
    client.birthDay = toSave.birthDay;
    client.gender = toSave.gender;
    client.character = toSave.character;
    client.phones = toSave.phones;
    client.registrationAddress = toSave.registrationAddress;
    return client;
  }

  @Override
  public ClientDetail getClientDetailById(long id) {
    ClientDetail clientDetail = ClientDetail.forSave(genders, characters, phoneDetails);

    if (clients != null) {
      for (Client client : clients) {
        if (client.id == id) {
          clientDetail = clientDetail.initalize(new ClientDetail(client.surname, client.name, client.patronymic, client.age, client.gender, genders, client.birthDay, client.character, characters, client.actualAddress, client.registrationAddress, client.phones, phoneDetails, client.id));
          //new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay,  client.character, characters, client.actualAddress, client.registrationAddress, client.phones, client.id);
        }
      }
    }
    return clientDetail;
  }

  @Override
  public ClientRecord saveClient(ClientToSave toSave) {
    ClientRecord clientRecord = new ClientRecord();
    if (clients != null) {
      for (Client client : clients) {
        if (client.id == toSave.clientID) {
          client = convertToSaveToClient(toSave);
          return convertClientToRecord(client);
        }
      }
      Client newClient = convertToSaveToClient(toSave);
      clients.add(newClient);
      return convertClientToRecord(newClient);
    }
    return clientRecord;
  }

  @Override
  public void deleteClient(long id) {
    if (clients != null) {

      for (Client client : clients) {
        if (client.id == id) {
          clients.remove(client);
          break;
          //return createRecordFromClient(client);
        }
      }
    }
  }


  @Override
  public ClientRecordListWrapper filterClients(ClientFilter clientFilter) {
    List<ClientRecord> filteredList = new ArrayList<ClientRecord>();
    int count = 0;
    if (clients != null) {
      for (Client client : clients) {
        if (client.name.toUpperCase().contains(clientFilter.name.toUpperCase()) &&
          client.surname.toUpperCase().contains(clientFilter.surname.toUpperCase()) &&
          client.patronymic.toUpperCase().contains(clientFilter.patronymic.toUpperCase())) {
          filteredList.add(convertClientToRecord(client));
          count++;
        }
      }
    }

    if(filteredList.size() > clientFilter.limit && clientFilter.limit != 0) {
      if(clientFilter.offset == 0) {
        filteredList = filteredList.subList(0, clientFilter.limit);
      } else if(clientFilter.offset * clientFilter.limit + clientFilter.limit > filteredList.size()) {
        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, filteredList.size());
      } else {
        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, clientFilter.offset * clientFilter.limit + clientFilter.limit);
      }
    }
    if("".equals(clientFilter.columnName)) {
      return new ClientRecordListWrapper(filteredList, count);
    } else {
      switch (clientFilter.columnName) {
        case "tot":
          if (clientFilter.isAsc) {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.totalBalance))
              .collect(Collectors.toList());
            return new ClientRecordListWrapper(filteredList, count);
          } else {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.totalBalance))
              .collect(Collectors.toList());
            Collections.reverse(filteredList);
            return new ClientRecordListWrapper(filteredList, count);
          }
        case "min":
          if(clientFilter.isAsc) {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.minBalance))
              .collect(Collectors.toList());
            return new ClientRecordListWrapper(filteredList, count);
          } else {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.minBalance))
              .collect(Collectors.toList());
            Collections.reverse(filteredList);
            return new ClientRecordListWrapper(filteredList, count);
          }
        case "max":
          if (clientFilter.isAsc) {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.maxBalance))
              .collect(Collectors.toList());
            return new ClientRecordListWrapper(filteredList, count);
          } else {
            filteredList = filteredList.stream()
              .sorted(Comparator.comparing(clientRecord -> clientRecord.maxBalance))
              .collect(Collectors.toList());
            Collections.reverse(filteredList);
            return new ClientRecordListWrapper(filteredList, count);
          }
      }
    }
    return new ClientRecordListWrapper(filteredList, count);
  }
}
