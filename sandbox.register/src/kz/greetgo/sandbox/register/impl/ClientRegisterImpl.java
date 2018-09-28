package kz.greetgo.sandbox.register.impl;

import kz.greetgo.db.Jdbc;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
  public BeanGetter<ClientDao> clientDao;
  int count = 0;


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
    PhoneDetail phoneDetail1 = new PhoneDetail(PhoneTypeDb.MOBILE, "Мобильный");
    PhoneDetail phoneDetail2 = new PhoneDetail(PhoneTypeDb.HOME, "Домашний");
    PhoneDetail phoneDetail3 = new PhoneDetail(PhoneTypeDb.WORK, "Рабочий");
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

//    characters = new ArrayList<>();
//    Character opennesCharacter = new Character(CharacterType.OPENNESS, "открытый");
//    Character agreeablenessCharacter = new Character(CharacterType.AGREEABLENESS, "любзеный");
//    Character conscientiousnessCharacter = new Character(CharacterType.CONSCIENTIOUSNESS, "добросовестный");
//    Character extraversionCharacter = new Character(CharacterType.EXTRAVERSION, "экстраверт");
//    Character neuroticismCharacter = new Character(CharacterType.NEUROTICISM, "невротичный");

//    characters.add(opennesCharacter);
//    characters.add(agreeablenessCharacter);
//    characters.add(conscientiousnessCharacter);
//    characters.add(extraversionCharacter);
//    characters.add(neuroticismCharacter);

    Date birthdayDate = new Date();

    try {
      birthdayDate = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
//    clients.add(new Client(1, "Sultanova", "Madina", "Mahammadnova", female, birthdayDate, opennesCharacter, 1000, 475, 5000, Address.empty(), new Address("Mamyr-4", "311", "8"), phones));
//    clients.add(new Client(2, "Asan", "Arman", "Kairatuly", male, birthdayDate, agreeablenessCharacter, 100, 0, 1425, Address.empty(), new Address("Mamyr-4", "311", "85"), phones));
//    clients.add(new Client(3, "Kuyan", "Kirill", "Kirillovich", female, birthdayDate, extraversionCharacter, 10, 8, 5545, Address.empty(), new Address("Mamyr-4", "311", "21"), phones));
//    clients.add(new Client(4, "Adam", "Vova", "Rei", male, birthdayDate, neuroticismCharacter, 40, 12, 5478, Address.empty(), new Address("Mamyr-4", "311", "73"), phones));
//    clients.add(new Client(5, "Malikaidar", "Symbat", "Isaevna", male, birthdayDate, neuroticismCharacter, 100, 74, 6574, Address.empty(), new Address("Mamyr-4", "311", "31"), phones));
//    clients.add(new Client(6, "Umirbekiva", "Madina", "MAliyasovna", male, birthdayDate, neuroticismCharacter, 10, 475, 96, Address.empty(), new Address("Mamyr-4", "311", "25"), phones));
//    clients.add(new Client(7, "Carrey", "Jim", "Nim", male, birthdayDate, neuroticismCharacter, 5, 252, 78, Address.empty(), new Address("Mamyr-4", "311", "82"), phones));
//    clients.add(new Client(8, "Tsay", "Roman", "Romanovich", male, birthdayDate, neuroticismCharacter, 250, 77, 68, Address.empty(), new Address("Pravda", "311", "72"), phones));
//    clients.add(new Client(6, "Sau", "Tut", "Tutovich", male, birthdayDate, neuroticismCharacter, 2370, 56, 15, Address.empty(), new Address("Mamyr-4", "311", "45"), phones));
//    clients.add(new Client(7, "Kiqu", "Erik", "", male, birthdayDate, neuroticismCharacter, 124, 74, 23, Address.empty(), new Address("Shalyapina", "311", "11K"), phones));
//    clients.add(new Client(8, "Dan", "Lan", "Balan", male, birthdayDate, neuroticismCharacter, 7, 825, 57, Address.empty(), new Address("Abai", "311", "9B"), phones));
//    clients.add(new Client(9, "Kiaus", "Simona", "", male, birthdayDate, neuroticismCharacter, 8, 546, 17, Address.empty(), new Address("Mamyr-4", "311", "9B"), phones));
//    clients.add(new Client(10, "Albu", "Andrea", "Gias", male, birthdayDate, neuroticismCharacter, 999, 90, 87, Address.empty(), new Address("Mamyr-1", "311", "9B"), phones));
//    clients.add(new Client(11, "Dun", "Era", "", male, birthdayDate, neuroticismCharacter, 201, 851, 63, Address.empty(), new Address("mkr-4", "311", "9B"), phones));
//    clients.add(new Client(12, "Luka", "Lukas", "Riu", male, birthdayDate, neuroticismCharacter, 9, 14, 45, Address.empty(), new Address("Mamyr-4", "311", "9B"), phones));

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

//  public Client convertToSaveToClient(ClientToSave toSave) {
//    Client client = new Client();
//    client.id = clients.get(clients.size() - 1).id + 1;
//
//    client.name = toSave.name;
//    client.surname = toSave.surname;
//    if (toSave.patronymic != null) {
//      client.patronymic = toSave.patronymic;
//    } else {
//      client.patronymic = "";
//    }
//    client.actualAddress = toSave.actualAddress;
//    client.birthDay = toSave.birthDay;
//    client.gender = toSave.gender;
//    client.character = toSave.character;
//    client.phones = toSave.phones;
//    client.registrationAddress = toSave.registrationAddress;
//    return client;
//  }

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
  public ClientDetail getClientDetailById(int id) {
//
//    ClientDetail clientDetail = ClientDetail.forSave(genders, characters, phoneDetails);
//
//
//    if (clients != null) {
//      for (Client client : clients) {
//        if (client.id == id) {
//          clientDetail = clientDetail.initalize(new ClientDetail(client.surname, client.name, client.patronymic, client.gender, genders, client.birthDay, client.character, characters, client.actualAddress, client.registrationAddress, client.phones, phoneDetails, client.id));
//        }
//      }
//    }
//    return clientDetail;

    this.genders = new ArrayList<>();
//    List<String> phoneD = clientDao.get().getPhoneD();
    for (GenderType type : GenderType.values()) {
      this.genders.add(new Gender(type, type.name));
    }
    this.characters = new ArrayList<>();
    for (CharacterType t : CharacterType.values()) {
      int charmId = clientDao.get().getCharmByName(t.toString());
      characters.add(new Character(charmId, t.name));
    }

    this.phoneDetails = new ArrayList<>();
    for (PhoneTypeDb ph : PhoneTypeDb.values()) {
      this.phoneDetails.add(new PhoneDetail(ph, ph.name));
    }
//    for (int i = 0; i < phoneD.size(); i++) {
//      for (PhoneType t : PhoneType.values()) {
//        if (t.name().equalsIgnoreCase(phoneD.get(i))) {
//          PhoneDetail phoneDetail = new PhoneDetail(t, phoneD.get(i));
//          this.phoneDetails.add(phoneDetail);
//          break;
//        }
//      }
//    }

    if (id == 0 || id == -1) {
      return ClientDetail.forSave(this.genders, this.characters, this.phoneDetails);
    } else {
      ClientDb client = clientDao.get().getClientDb(id);
      String charm = clientDao.get().getCharmName(client.id);
      Address regAddress = clientDao.get().getAddress(client.id, "REG");
      Address factAddress = clientDao.get().getAddress(client.id, "FACT");
      List<ClientPhoneDb> phoneList = clientDao.get().phoneList(client.id);
      CharmDb charmDb = clientDao.get().getCharm(client.id);


      Character cType;
      if (CharacterType.parseOrNull(charmDb.name) != null) {
        cType = new Character(charmDb.id, CharacterType.parseOrNull(charmDb.name).name);
      } else {
        cType = new Character(charmDb.id, "");
      }
//      for (CharacterType t : CharacterType.values()) {
//        if (t.name().equalsIgnoreCase(charm)) {
//          type = t;
//        }
//      }

      List<Phone> phones = new ArrayList<>();


//      for (int i = 0; i < phoneList.size(); i++) {
      for (ClientPhoneDb phone : phoneList) {
        if (PhoneTypeDb.parseOrNull(phone.type) != null) {
          Phone ph = new Phone(new PhoneDetail(PhoneTypeDb.parseOrNull(phone.type),
            PhoneTypeDb.parseOrNull(phone.type).name), phone.number);
          phones.add(ph);
        }
      }
//        for (PhoneDetail t : this.phoneDetails) {
//          if (t.type == phoneList.get(i).type) {
//        }


//      DateFormat srcDf = new SimpleDateFormat("dd/MM/yyyy");
//      Date birthDate = srcDf.format(client.birthDate);

//      Date birthdayDate = new Date();
//
//    try {
//      birthdayDate = new SimpleDateFormat("dd/MM/yyyy")
//        .parse("20/12/1998");
//    } catch (ParseException e) {
//      e.printStackTrace();
//    }



      ClientDetail clientDetail = new ClientDetail(client.surname, client.name, client.patronymic,
        new Gender(client.gender, client.gender.toString()), this.genders, client.birthDate,
        cType, this.characters, factAddress, regAddress, phones, this.phoneDetails, client.id);

      return clientDetail;
    }

    //return null;
  }


  @Override
  public ClientRecord saveClient(ClientToSave toSave) {
    ClientRecord clientRecord = new ClientRecord();
//    int charmId = toSave.character.id;////clientDao.get().getCharmById(toSave.character.id);//////getCharmByName
    ClientDb client = new ClientDb();

    client = client.convertToSaveToClient(toSave);

    if (toSave.clientID == -1 || toSave.clientID == 0) {
      toSave.clientID = clientDao.get().insertClient(client);
      client.id = toSave.clientID;
    }
    String characterName = clientDao.get().getCharmName(toSave.character.id);
    if (CharacterType.parseOrNull(characterName) != null) {
      toSave.character.type = CharacterType.parseOrNull(characterName).name;
    }
    clientDao.get().saveOrUpdateClient(client);

    ClientAddrDb address = new ClientAddrDb();
    address = address.getAddressFromToSave(toSave, "REG");
    clientDao.get().saveOrUpdateAddress(address);
    address = address.getAddressFromToSave(toSave, "FACT");
    clientDao.get().saveOrUpdateAddress(address);


    List<Phone> phoneList = Phone.getPhoneListFromToSave(toSave);
    for (Phone phone : phoneList) {

      if (phone.oldNumber == null) {
        phone.oldNumber = phone.number;
      }
      clientDao.get().deactualPhone(toSave.clientID, phone.oldNumber);
      clientDao.get().saveOrUpdatePhone(toSave.clientID, phone.number, phone.detail.type.toString());
    }

//    ClientAccountDb clientAccountDb;

    /*

    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);

    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);

    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);
     */
//    TransactionInfo transactionInfo = clientDao.get().getTransactionInfo(toSave.clientID);
//
//    if (transactionInfo == null) {
//      transactionInfo = new TransactionInfo();
//      transactionInfo.id = toSave.clientID;
//      transactionInfo.maxBalance = 0;
//      transactionInfo.minBalance = 0;
//      transactionInfo.maxBalance = 0;
//    }


//    if (clients != null) {
//      for (Client client : clients) {
//        if (client.id == toSave.clientID) {
//          client = updateClientFromToSave(toSave, client);
//          return convertClientToRecord(client);
//        }
//      }
//      Client newClient = convertToSaveToClient(toSave);
//      clients.add(newClient);
//      return convertClientToRecord(newClient);
//    }
    return clientRecord.convertToSaveToClientRecord(toSave);//, transactionInfo);
  }

  @Override
  public void deleteClient(int id) {
//    if (clients != null) {
//
//      for (Client client : clients) {
//        if (client.id == id) {
//          clients.remove(client);
//          break;
//        }
//      }
//    }

    List<ClientPhoneDb> phoneDb = this.clientDao.get().getPhoneList(id);
    this.clientDao.get().deactualClient(id);
    List<Integer> accounts = this.clientDao.get().deactualAccounts(id);
    for (Integer acc : accounts) {
      this.clientDao.get().deactualTransactions(acc);
    }
    this.clientDao.get().deactualAddress(id, "REG");
    this.clientDao.get().deactualAddress(id, "FACT");
    for (ClientPhoneDb phone : phoneDb) {
      this.clientDao.get().deactualPhone(phone.client, phone.number);
    }
  }


  private ClientRecordListWrapper getRecordsFromDb(ClientFilter clientFilter) {

    List<ClientRecord> recordList = new ArrayList<>();

    //SQL sql = new SQL();
    String sq = "select\n" +
      "  x1.surname,\n" +
      "  x1.name,\n" +
      "  x1.patronymic,\n" +
      "  x1.birth_date ,\n" +
      "  x.sum,\n" +
      "  x.min,\n" +
      "  x.max,\n" +
      "  x1.id,\n" +
      "  x2.name,\n" +
      "  x2.id,\n" +
      "  count(x1.id)\n" +
      "  over (\n" +
      "    partition by 1 )\n" +
      "from (\n" +
      "       select\n" +
      "         max(a.money),\n" +
      "         min(a.money),\n" +
      "         sum(a.money),\n" +
      "         c.id\n" +
      "       from client as c\n" +
      "         left join client_account as a\n" +
      "           on c.id = a.client\n" +
      "       group by c.id\n" +
      "     ) x\n" +
      "  join client x1 on x1.id = x.id\n" +
      "  join charm x2 on x2.id = x1.charm\n" +
//      "  join client_account x3 on x1.id = x3.client\n" +
      "where";

    ////where x1.name = 'M'
    ////      and x1.surname = 'MM'
    ////      and x1.patronymic = 'MMM'
    ////      and x1.id >= 0
    ////order by x1.name asc
    ////limit 1;

    ArrayList<String> prepareStValue = new ArrayList<>();
    if (!"".equals(clientFilter.name)) {
      sq += " x1.name = ?\n";
      prepareStValue.add(clientFilter.name);
    }
    if (!"".equals(clientFilter.surname)) {
      if (prepareStValue.size() != 0) {
        sq += " and";
      }
      sq += " x1.surname = ?\n";
      prepareStValue.add(clientFilter.surname);
    }
    if (!"".equals(clientFilter.patronymic)) {
      if (prepareStValue.size() != 0) {
        sq += " and";
      }
      sq += " x1.patronymic = ?\n";
      prepareStValue.add(clientFilter.patronymic);
    }
    if (clientFilter.offset >= 0) {
      if (prepareStValue.size() != 0) {
        sq += " and";
      }
      sq += " x1.id > ";// >=


      if (clientFilter.offset == 0) {
        sq = sq.concat(Integer.toString(0) + "\n");
      } else {
        sq = sq.concat(Integer.toString(clientFilter.offset * clientFilter.limit) + "\n");
      }
      sq += " and x1.actual = true\n";

//      sq = sq.concat(Integer.toString(clientFilter.offset + 1) + "\n");///
//      prepareStValue.add(Integer.toString(clientFilter.offset));
    }
    //if (clientFilter.offset == 0) {
    ////        filteredList = filteredList.subList(0, clientFilter.limit);
    ////      } else if (clientFilter.offset * clientFilter.limit + clientFilter.limit > filteredList.size()) {
    ////        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, filteredList.size());
    ////      } else {
    ////        filteredList = filteredList.subList(clientFilter.offset * clientFilter.limit, clientFilter.offset * clientFilter.limit + clientFilter.limit);
    ////      }

    sq += "order by  x1.id asc\n";

    if (!"".equals(clientFilter.columnName) && clientFilter.columnName != null) {

      sq = sq.concat(", " + clientFilter.columnName + " ");
//      prepareStValue.add(clientFilter.columnName);
      if (clientFilter.isAsc) {
//        prepareStValue.add("asc");
        sq = sq.concat("asc ");
      } else {
//        prepareStValue.add("desc");
        sq = sq.concat("desc ");
      }
    }
    if (clientFilter.limit >= 0) {
      sq += "limit ";
      sq = sq.concat(Integer.toString(clientFilter.limit) + ";");
//      prepareStValue.add(Integer.toString(clientFilter.limit));
    }
    final String newSQL = sq;

    System.out.println("newSQL: \n" + newSQL);
    jdbc.get().execute(con -> {
      try (PreparedStatement ps = con.prepareStatement(newSQL)) {
//        prepareStValue.forEach(item -> {
//          ps.setObject(prepareStValue.indexOf(item)+1, item);
//        });
        for (int i = 0; i < prepareStValue.size(); i++) {
          ps.setObject(i + 1, prepareStValue.get(i));
        }
        try (ResultSet rs = ps.executeQuery()) {
          while (rs.next()) {
            ClientRecord clientRecord = new ClientRecord();
            clientRecord.fio = rs.getString(1) + " "
              + rs.getString(2) + " " + rs.getString(3);

            Date d = rs.getDate(4);
            System.out.println("Date is: " + d);

            LocalDate currentDate = LocalDate.now();

            LocalDate birthDate = Instant.ofEpochMilli(d.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            if ((birthDate != null) && (currentDate != null)) {
              clientRecord.age = Period.between(birthDate, currentDate).getYears();
            } else {
              clientRecord.age = 0;
            }

            clientRecord.totalBalance = (int) rs.getDouble(5);
            clientRecord.minBalance = (int) rs.getFloat(6);
            clientRecord.maxBalance = (int) rs.getFloat(7);
            clientRecord.clientId = rs.getInt(8);

            if (CharacterType.parseOrNull(rs.getString(9)) != null) {
              CharacterType ch = CharacterType.parseOrNull(rs.getString(9));
              clientRecord.character = new Character(rs.getInt(10), ch.name);
            } else {// (clientRecord.character == null) {
              clientRecord.character = new Character(11, "невротичный");
            }
            count = rs.getInt(11);
            recordList.add(clientRecord);
          }
        }
      }
      return null;
    });


    return new ClientRecordListWrapper(recordList, count);
  }


  @Override
  public ClientRecordListWrapper filterClients(ClientFilter clientFilter) {

    return this.getRecordsFromDb(clientFilter);

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


  public static void main(String[] args) {
    System.out.println("ClientRegisterImpl.main");
  }
}
