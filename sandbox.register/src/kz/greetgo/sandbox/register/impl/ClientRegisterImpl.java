package kz.greetgo.sandbox.register.impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.db.ClientDb;
import kz.greetgo.sandbox.controller.register.ClientRegister;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {
  {
    createClients();
  }

  List<Client> clients = null;
  List<ClientRecord> clientRecords = null;
  List<Character> characters = null;
  List<Gender> genders = null;
  List<PhoneDetail> phoneDetails = null;


  public BeanGetter<Jdbc> jdbc;

//  class A {
//    public int asd = 0;
//  }
//
//
//  public void test() {
//    A a = new A();
//    List<>
//    String sql = "select 1 as asd where 1=? and true = ?";
//
//    if ("".equals("")) {
//      sql += " and surname = ?";
//    }
//      ///
//    jdbc.get().execute(con -> {
//      try (PreparedStatement ps = con.prepareStatement(sql)) {
//        ps.setObject(1, 1);
//        ps.setBoolean(2, false);
//        try (ResultSet rs = ps.executeQuery()) {
//          while (rs.next()) {
//            a.asd = rs.getInt("asd");
//          }
//        }
//      }
//      return null;
//    });
//    ///
//
//    System.out.println(a.asd);
//
//  }


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
    clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", female, birthdayDate, opennesCharacter, 1000, 475, 5000, Address.empty(), new Address("Mamyr-4", "311", "8"), phones));
    clients.add(new Client(2, "Asan", "Arman", "Kairatuly", male, birthdayDate, agreeablenessCharacter, 100, 0, 1425, Address.empty(), new Address("Mamyr-4", "311", "85"), phones));
    clients.add(new Client(3, "Kuyan", "Kirill", "Kirillovich", female, birthdayDate, extraversionCharacter, 10, 8, 5545, Address.empty(), new Address("Mamyr-4", "311", "21"), phones));
    clients.add(new Client(4, "Adam", "Vova", "Rei", male, birthdayDate, neuroticismCharacter, 40, 12, 5478, Address.empty(), new Address("Mamyr-4", "311", "73"), phones));
    clients.add(new Client(5, "Malikaidar", "Symbat", "Isaevna", male, birthdayDate, neuroticismCharacter, 100, 74, 6574, Address.empty(), new Address("Mamyr-4", "311", "31"), phones));
    clients.add(new Client(6, "Umirbekiva", "Madina", "MAliyasovna", male, birthdayDate, neuroticismCharacter, 10, 475, 96, Address.empty(), new Address("Mamyr-4", "311", "25"), phones));
    clients.add(new Client(7, "Carrey", "Jim", "Nim", male, birthdayDate, neuroticismCharacter, 5, 252, 78, Address.empty(), new Address("Mamyr-4", "311", "82"), phones));
    clients.add(new Client(8, "Tsay", "Roman", "Romanovich", male, birthdayDate, neuroticismCharacter, 250, 77, 68, Address.empty(), new Address("Pravda", "311", "72"), phones));
    clients.add(new Client(6, "Sau", "Tut", "Tutovich", male, birthdayDate, neuroticismCharacter, 2370, 56, 15, Address.empty(), new Address("Mamyr-4", "311", "45"), phones));
    clients.add(new Client(7, "Kiqu", "Erik", "", male, birthdayDate, neuroticismCharacter, 124, 74, 23, Address.empty(), new Address("Shalyapina", "311", "11K"), phones));
    clients.add(new Client(8, "Dan", "Lan", "Balan", male, birthdayDate, neuroticismCharacter, 7, 825, 57, Address.empty(), new Address("Abai", "311", "9B"), phones));
    clients.add(new Client(9, "Kiaus", "Simona", "", male, birthdayDate, neuroticismCharacter, 8, 546, 17, Address.empty(), new Address("Mamyr-4", "311", "9B"), phones));
    clients.add(new Client(10, "Albu", "Andrea", "Gias", male, birthdayDate, neuroticismCharacter, 999, 90, 87, Address.empty(), new Address("Mamyr-1", "311", "9B"), phones));
    clients.add(new Client(11, "Dun", "Era", "", male, birthdayDate, neuroticismCharacter, 201, 851, 63, Address.empty(), new Address("mkr-4", "311", "9B"), phones));
    clients.add(new Client(12, "Luka", "Lukas", "Riu", male, birthdayDate, neuroticismCharacter, 9, 14, 45, Address.empty(), new Address("Mamyr-4", "311", "9B"), phones));

  }

  @Override
  public List<ClientRecord> getClientList() {
    clientRecords = new ArrayList<>();
    if (clients != null) {
      for (Client client : clients) {
        clientRecords.add(convertClientToRecord(client));
      }
    }
    return clientRecords;
  }

  public ClientRecord convertClientToRecord(Client client) {
    ClientRecord clientRecord = new ClientRecord();
    clientRecord.fio = client.surname + " " + client.name + " " + client.patronymic;
    LocalDate currentDate = LocalDate.now();
    LocalDate birthDate = client.birthDay.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    if ((birthDate != null) && (currentDate != null)) {
      clientRecord.age = Period.between(birthDate, currentDate).getYears();
    } else {
      clientRecord.age = 0;
    }
    clientRecord.totalBalance = client.totalBalance;
    clientRecord.minBalance = client.minBalance;
    clientRecord.maxBalance = client.maxBalance;
    clientRecord.character = client.character;
    clientRecord.clientId = client.id;
    return clientRecord;
  }

  public Client convertToSaveToClient(ClientToSave toSave) {
    Client client = new Client();
    client.id = clients.get(clients.size() - 1).id + 1;

    client.name = toSave.name;
    client.surname = toSave.surname;
    if (toSave.patronymic != null) {
      client.patronymic = toSave.patronymic;
    } else {
      client.patronymic = "";
    }
    client.actualAddress = toSave.actualAddress;
    client.birthDay = toSave.birthDay;
    client.gender = toSave.gender;
    client.character = toSave.character;
    client.phones = toSave.phones;
    client.registrationAddress = toSave.registrationAddress;
    return client;
  }

  public Client updateClientFromToSave(ClientToSave toSave, Client client) {
    client.name = toSave.name;
    client.surname = toSave.surname;
    if (toSave.patronymic != null) {
      client.patronymic = toSave.patronymic;
    } else {
      client.patronymic = "";
    }
    client.actualAddress = toSave.actualAddress;
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
          clientDetail = clientDetail.initalize(new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay, client.character, characters, client.actualAddress, client.registrationAddress, client.phones, phoneDetails, client.id));
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
          client = updateClientFromToSave(toSave, client);
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
        }
      }
    }
  }
//  String sql = "select 1 as asd where 1=? and true = ?";
//
//    if ("".equals("")) {
//      sql += " and surname = ?";
//    }
//      ///
//    jdbc.get().execute(con -> {
//      try (PreparedStatement ps = con.prepareStatement(sql)) {
//        ps.setObject(1, 1);
//        ps.setBoolean(2, false);
//        try (ResultSet rs = ps.executeQuery()) {
//          while (rs.next()) {
//            a.asd = rs.getInt("asd");
//          }
//        }
//      }
//      return null;
//    });


//  create table asd (
//    id   serial,
//    name varchar(200)
//
//);
//
//
//  select *
//  from asd;
//
//
//  with asd as (
//    insert into asd (name) values ('Madina')
//  returning id
//)
//
//  select *
//  from asd;
//
//  insert into charm (name, description, energy) values ('aa', 'aaa', 1);
//
//  insert into client (surname, name, patronymic, gender, birth_date, charm)
//  values ('MM', 'M', 'MMM', 'MALE', '2001-04-10', 1);
//
//  insert into client_account values (1, 1, 14000.0, '1234123412341234', null);
//
//  insert into transaction_type (code, name) values ('code', 'name');
//
//  insert into client_account_transaction (account, money, finished_at, type) values (1, 500.0, null, 1);
//
//  select
//  c.name,
//  c.surname,
//  a.money,
//  tt.name,
//  t.money
//  from client as c
//  inner join client_account as a
//  on c.id = a.client
//  inner join client_account_transaction as t
//  on a.id = t.account
//  inner join transaction_type as tt
//  on t.type = tt.id;
//

  private List<ClientDb> getClientsFromDb() {
    List<ClientDb> clientDbList = new ArrayList<>();
    String sql = "select ";


    return clientDbList;
  }

  @Override
  public ClientRecordListWrapper filterClients(ClientFilter clientFilter) {
    List<ClientRecord> filteredList = new ArrayList<ClientRecord>();
    int count = 0;

    List<ClientDb> clients = this.getClientsFromDb();


    return new ClientRecordListWrapper(filteredList, count);
//    List<ClientRecord> filteredList = new ArrayList<ClientRecord>();
//    int count = 0;
//    if (clients != null) {
//      for (Client client : clients) {
//        if (client.name.toUpperCase().contains(clientFilter.name.toUpperCase()) &&
//          client.surname.toUpperCase().contains(clientFilter.surname.toUpperCase()) &&
//          client.patronymic.toUpperCase().contains(clientFilter.patronymic.toUpperCase())) {
//          filteredList.add(convertClientToRecord(client));
//          count++;
//        }
//      }
//    }
//
//    if (filteredList.size() > clientFilter.limit && clientFilter.limit != 0) {
//      if (clientFilter.offset == 0) {
//        filteredList = filteredList.subList(0, clientFilter.limit);
//      } else if (clientFilter.offset * clientFilter.limit + clientFilter.limit > filteredList.size()) {
//        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, filteredList.size());
//      } else {
//        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, clientFilter.offset * clientFilter.limit + clientFilter.limit);
//      }
//    }
//    if ("".equals(clientFilter.columnName)) {
//      return new ClientRecordListWrapper(filteredList, count);
//    } else {
//      switch (clientFilter.columnName) {
//        case "tot":
//          if (clientFilter.isAsc) {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.totalBalance))
//              .collect(Collectors.toList());
//            return new ClientRecordListWrapper(filteredList, count);
//          } else {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.totalBalance))
//              .collect(Collectors.toList());
//            Collections.reverse(filteredList);
//            return new ClientRecordListWrapper(filteredList, count);
//          }
//        case "min":
//          if (clientFilter.isAsc) {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.minBalance))
//              .collect(Collectors.toList());
//            return new ClientRecordListWrapper(filteredList, count);
//          } else {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.minBalance))
//              .collect(Collectors.toList());
//            Collections.reverse(filteredList);
//            return new ClientRecordListWrapper(filteredList, count);
//          }
//        case "max":
//          if (clientFilter.isAsc) {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.maxBalance))
//              .collect(Collectors.toList());
//            return new ClientRecordListWrapper(filteredList, count);
//          } else {
//            filteredList = filteredList.stream()
//              .sorted(Comparator.comparing(clientRecord -> clientRecord.maxBalance))
//              .collect(Collectors.toList());
//            Collections.reverse(filteredList);
//            return new ClientRecordListWrapper(filteredList, count);
//          }
//      }
//    }
//    return new ClientRecordListWrapper(filteredList, count);
  }
}
