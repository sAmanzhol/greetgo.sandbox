package kz.greetgo.sandbox.register.test.util.test_utils;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.Character;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.util.RND;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class RandomEntity {

  public Client client() {
    Client client = new Client();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    client.birthDay = new Date();
    client.gender = new Gender(GenderType.FEMALE, "");
    client.character = new Character(CharacterType.AGREEABLENESS, "");
    return client;
  }

  public ClientDb clientDb() {
    ClientDb client = new ClientDb();
    client.name = RND.str(10);
    client.surname = RND.str(10);
    client.patronymic = RND.str(10);
    Date birthdayDate = new Date();

    try {
      birthdayDate = new SimpleDateFormat("dd/MM/yyyy")
        .parse("20/12/1998");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    client.birthDate = birthdayDate;
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
    clientDb.gender = GenderType.MALE;
    clientDb.charm = charmId;

    return clientDb;
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
    ClientAccountDb clientAccountDb = new ClientAccountDb();
    clientAccountDb.client = clientId;
    clientAccountDb.money = (float) RND.plusInt(9);
    clientAccountDb.registeredAt = new Timestamp(1);
    return clientAccountDb;
  }

  public TransactionTypeDb transactionTypeDb() {
    TransactionTypeDb trTypeDb = new TransactionTypeDb();
    trTypeDb.code = "12345678";
    trTypeDb.name = "out";
    return trTypeDb;
  }

  public ClientAccountTransactionDb clientAccountTransactionDb(int tTypeId, int clientA) {

    ClientAccountTransactionDb accountTransactionDb = new ClientAccountTransactionDb();
    accountTransactionDb.account = clientA;
    accountTransactionDb.money = (float) RND.plusInt(6);
    accountTransactionDb.finishedAt = new Timestamp(1);
    accountTransactionDb.type = tTypeId;
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
}
