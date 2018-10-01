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
      phoneList.add(phoneDb);
    }
    return phoneList;
  }

  public ClientPhoneDb clientPhoneDb(int clientID, Phone ph) {

    ClientPhoneDb phoneDb = new ClientPhoneDb();
    phoneDb.client = clientID;
    phoneDb.number = ph.number;
    phoneDb.type = "HOME";
    return phoneDb;
  }

  public Phone phone(String oldNumber) {

    Phone phone = new Phone();
    phone.detail = new PhoneDetail(PhoneTypeDb.parseOrNull("WORK"), PhoneTypeDb.parseOrNull("WORK").name);
    phone.oldNumber = oldNumber;
    phone.number = Integer.toString(RND.plusInt(89999));
    return phone;
  }

  public List<ClientPhoneDb> clientPhoneDbList(int clientID) {
    List<ClientPhoneDb> phoneList = new ArrayList<>();

    for (int i = 0; i < 3; i++) {
      ClientPhoneDb phoneDb = new ClientPhoneDb();
      phoneDb.client = clientID;
      phoneDb.number = RND.intStr(8);
      if (PhoneTypeDb.parseOrNull("WORK") != null) {
        phoneDb.type = PhoneTypeDb.parseOrNull("WORK").toString();
      }
      phoneList.add(phoneDb);
    }
    return phoneList;
  }

  public ClientDb clientDb(int charmId, String param, int mode) {
    ClientDb clientDb = clientDb(charmId);
    switch (mode) {
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


  public ClientToSave clientToSave() {
    ClientToSave toSave = new ClientToSave();
    toSave.clientID = RND.plusInt(2);
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
    toSave.character = new Character(11, CharacterType.AGREEABLENESS.toString());
    toSave.gender = new Gender(GenderType.FEMALE, "female");
    if (PhoneTypeDb.parseOrNull("HOME") != null) {
      toSave.phones.add(new Phone(new PhoneDetail(PhoneTypeDb.parseOrNull("HOME"), PhoneTypeDb.parseOrNull("HOME").name), "111", "111"));
    }
    if (PhoneTypeDb.parseOrNull("WORK") != null) {
      toSave.phones.add(new Phone(new PhoneDetail(PhoneTypeDb.parseOrNull("WORK"), PhoneTypeDb.parseOrNull("WORK").name), "2222", "2222"));
    }
    return toSave;
  }

  public ClientToSave clientToSaveE() {
    ClientToSave toSave = new ClientToSave();
    toSave.clientID = -1;
    toSave.surname = "";
    toSave.name = "";
    toSave.patronymic = "";
    toSave.actualAddress = new Address();
    toSave.registrationAddress = new Address();
    toSave.birthDay = new Date();
    toSave.character = new Character();
    toSave.gender = new Gender();
    toSave.phones.add(new Phone());
    return toSave;
  }


}
