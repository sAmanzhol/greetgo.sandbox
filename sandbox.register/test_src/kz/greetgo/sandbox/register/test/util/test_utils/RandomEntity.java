package kz.greetgo.sandbox.register.test.util.test_utils;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.util.RND;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Bean
public class RandomEntity {

//  public Client client() {
//    Client client = new Client();
//    client.name = RND.str(10);
//    client.surname = RND.str(10);
//    client.patronymic = RND.str(10);
//    client.birthDay = new Date();
//    client.gender = new Gender(GenderType.FEMALE, "");
//    client.character = new Character(CharacterType.AGREEABLENESS, "");
//    return client;
//  }

  public ClientDb clientDb() {
    ClientDb client = new ClientDb();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    client.birthDate = new Date();
    try {
      client.birthDate = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return client;
  }

  public CharmDb charmDb() {
    CharmDb charmDb = new CharmDb();
    charmDb.name = RND.str(5);
    charmDb.description = RND.str(10);
    charmDb.energy = (float) RND.plusInt(7);
    return charmDb;
  }

  public ClientDb clientDb(int charmId) {
    ClientDb clientDb = new ClientDb();
    clientDb.name = RND.str(5);
    clientDb.patronymic = RND.str(4);
    clientDb.surname = RND.str(4);
    clientDb.birthDate = new Date();
    try {
      clientDb.birthDate = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    clientDb.gender = GenderType.MALE;
    clientDb.charm = charmId;

    return clientDb;
  }

  public ClientDb clientDb(ClientToSave toSave, int charmId) {

    ClientDb clientDb = new ClientDb();
    clientDb.id = toSave.clientID;
    clientDb.name = toSave.name;
    clientDb.patronymic = toSave.patronymic;
    clientDb.surname = toSave.surname;
    clientDb.birthDate = toSave.birthDay;
    clientDb.charm = charmId;
    clientDb.gender = toSave.gender.type;
    return clientDb;
  }

  public ClientAddrDb clientAddrDb(ClientToSave toSave, String type) {
    ClientAddrDb clientAddrDb = new ClientAddrDb();

    clientAddrDb.client
      = toSave.clientID;
    if (AddressTypeDb.parseOrNull(type) != null) {
      clientAddrDb.type = AddressTypeDb.parseOrNull(type);
    }
    if ("FACT".equals(type)) {
      clientAddrDb.street = toSave.actualAddress.street;
      clientAddrDb.flat = toSave.actualAddress.flat;
      clientAddrDb.house = toSave.actualAddress.house;
    } else if ("REG".equals(type)) {
      clientAddrDb.street = toSave.registrationAddress.street;
      clientAddrDb.flat = toSave.registrationAddress.flat;
      clientAddrDb.house = toSave.registrationAddress.house;
    }
    return clientAddrDb;
  }

  public ClientAddrDb clientAddrDb(int clientID, String type) {
    ClientAddrDb clientAddrDb = new ClientAddrDb();

    clientAddrDb.client = clientID;
    if (AddressTypeDb.parseOrNull(type) != null) {
      clientAddrDb.type = AddressTypeDb.parseOrNull(type);
    }
    clientAddrDb.street = RND.intStr(7);
    clientAddrDb.flat = RND.intStr(7);
    clientAddrDb.house = RND.intStr(7);
    return clientAddrDb;
  }

  public List<ClientPhoneDb> clientPhoneDb(ClientToSave toSave) {
    List<ClientPhoneDb> phoneList = new ArrayList<>();

    for (Phone phone : toSave.phones) {
      ClientPhoneDb phoneDb = new ClientPhoneDb();
      phoneDb.client = toSave.clientID;
      phoneDb.number = phone.number;
      phoneDb.oldNumber = phone.oldNumber;
      phoneDb.type = PhoneTypeDb.parseOrNull(phone.detail.type.toString());
      phoneList.add(phoneDb);
    }
    return phoneList;
  }

  public List<ClientPhoneDb> clientPhoneDb(int clientID) {
    List<ClientPhoneDb> phoneList = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      ClientPhoneDb phoneDb = new ClientPhoneDb();
      phoneDb.client = clientID;
      phoneDb.number = RND.intStr(8);
      phoneDb.oldNumber = phoneDb.number;
      phoneDb.type = PhoneTypeDb.parseOrNull("WORK");
      phoneList.add(phoneDb);
    }
    return phoneList;
  }

  public ClientDb clientDb(int charmId, String param, int ch) {
    ClientDb clientDb = clientDb(charmId);
    switch (ch) {
      case 1:
        clientDb.name = param;
        break;
      case 2:
        clientDb.surname = param;
        break;
      case 3:
        clientDb.patronymic = param;
        break;
      default:
        break;
    }

    return clientDb;
  }

  public ClientAccountDb clientAccountDb(int clientId) {
    ClientAccountDb account = new ClientAccountDb();
    account.client = clientId;
    account.registeredAt = new Timestamp(new Date().getTime());
    account.money = RND.plusLong(9999);
    account.number = RND.intStr(7);
    account.actual = true;
    return account;
  }

  public TransactionTypeDb transactionTypeDb() {
    TransactionTypeDb trTypeDb = new TransactionTypeDb();
    trTypeDb.code = RND.str(7);
    trTypeDb.name = RND.str(8);
    trTypeDb.actual = true;
    return trTypeDb;
  }

  public ClientAccountTransactionDb clientAccountTransactionDb(int tTypeId, int clientA) {

    ClientAccountTransactionDb accountTransactionDb = new ClientAccountTransactionDb();
    accountTransactionDb.account = clientA;
    accountTransactionDb.money = RND.plusLong(9999);
    accountTransactionDb.finishedAt = new Timestamp(new Date().getTime());
    accountTransactionDb.type = tTypeId;
    accountTransactionDb.actual = true;
    return accountTransactionDb;
  }

  public ClientRecord record() {

//    ClientRecord clientRecord = new ClientRecord();
//
//    clientRecord.
//    return clientRecord;
    return null;

  }

  public ClientFilter filter() {
    return new ClientFilter();
  }

  public ClientFilter filter(String column) {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.columnName = column;
    return clientFilter;
  }

  public ClientFilter filter(int limit) {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.limit = limit;
    return clientFilter;
  }

  public ClientFilter filterE() {
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.name = "";
    clientFilter.surname = "";
    clientFilter.patronymic = "";
    clientFilter.limit = 100;
    clientFilter.offset = 0;
    clientFilter.isAsc = true;
    return clientFilter;
  }

//  public List<CharmDb> charmDbList(){
//    List<CharmDb> charmDbList = new ArrayList<>();
//
//    return charmDbList;
//  }


  public ClientToSave clientToSave() {
    ClientToSave toSave = new ClientToSave();
    toSave.surname = RND.str(6);
    toSave.name = RND.str(5);
    toSave.patronymic = RND.str(6);
    toSave.actualAddress = new Address(RND.str(2), RND.str(3), RND.str(4));
    toSave.registrationAddress = new Address(RND.str(2), RND.str(3), RND.str(4));
    toSave.birthDay = new Date();
    try {
      toSave.birthDay = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    toSave.character = new Character(CharacterType.AGREEABLENESS, "auuh");
    toSave.gender = new Gender(GenderType.FEMALE, "female");
    toSave.phones.add(new Phone(new PhoneDetail(PhoneType.HOME, "home"), Integer.toString(RND.plusInt(152556)), "111"));
    toSave.phones.add(new Phone(new PhoneDetail(PhoneType.WORK, "work"), Integer.toString(RND.plusInt(156347)), "2222"));
    return toSave;
  }


}
