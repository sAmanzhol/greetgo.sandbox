package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.sandbox.register.test.util.test_utils.RandomEntity;
import org.apache.ibatis.exceptions.PersistenceException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {
  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<RandomEntity> randomEntity;


  public void createRequired() {
    this.clientTestDao.get().insertDefaultCharms();
  }

  @BeforeMethod
  void cleaningDb() {

    this.clientTestDao.get().truncateClient();
    this.clientTestDao.get().truncateAccount();
    this.clientTestDao.get().truncateAccountTransaction();
    this.clientTestDao.get().truncateAddress();
    this.clientTestDao.get().truncatePhones();
    this.clientTestDao.get().trunccateTransactionType();
    this.clientTestDao.get().truncateCharms();
    this.createRequired();
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testFilter_NullFilter() {
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(null);
    //
    //
  }

  @Test
  public void testFilter_filter_emptyFilter() {
    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);


    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
//
//    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
//    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);
//
//    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
//    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(clientRecordListWrapper.records.size());
  }

  @Test
  public void testFilter_count() {
    for (int i = 0; i < 20; i++) {
      CharmDb charm = this.randomEntity.get().charmDb();
      int charmId = clientTestDao.get().insertCharm(charm);

      ClientDb clientDb = this.randomEntity.get().clientDb(charmId, Integer.toString(i) + " name", 1);
      int clientId = clientTestDao.get().insertClientDb(clientDb);
    }
    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.limit = 2;
    clientFilter.offset = 1;
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);

    }
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(2);
    assertThat(clientRecordListWrapper.count).isEqualTo(20);

  }

  @Test
  public void testFilter_name() {

    for (int i = 0; i < 3; i++) {
      CharmDb charm = this.randomEntity.get().charmDb();
      int charmId = clientTestDao.get().insertCharm(charm);

      ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Madina", 1);
      int clientId = clientTestDao.get().insertClientDb(clientDb);
    }
    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.name = "Madina";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(3);
  }

  @Test
  public void testFilter_patronymic() {


    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Abai", 3);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.patronymic = "Abai";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
//    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
//      System.out.println(clientRecordListWrapper.records.get(i).clientId);
//      System.out.println(clientRecordListWrapper.records.get(i).fio);
//
//    }
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);

  }


  @Test
  public void testFilter_surname() {

    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Asa", 2);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.surname = "Asa";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
//    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
//      System.out.println(clientRecordListWrapper.records.get(i).clientId);
//      System.out.println(clientRecordListWrapper.records.get(i).fio);
//
//    }
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);

  }


  @Test
  public void testFilter_limit() {

    for (int i = 0; i < 5; i++) {
      CharmDb charm = this.randomEntity.get().charmDb();
      int charmId = clientTestDao.get().insertCharm(charm);

      ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "AAAAAAA", 2);
      int clientId = clientTestDao.get().insertClientDb(clientDb);
    }

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.limit = 1;
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);
  }

  @Test
  public void testFilter_offset() {
    for (int i = 0; i < 5; i++) {
      CharmDb charm = this.randomEntity.get().charmDb();
      int charmId = clientTestDao.get().insertCharm(charm);

      ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
      int clientId = clientTestDao.get().insertClientDb(clientDb);
    }
    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.offset = 2;
    clientFilter.limit = 1;
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      assertThat(clientRecordListWrapper.records.get(i).clientId).isEqualTo(3); // => 2 + 1
    }
    assertThat(clientRecordListWrapper).isNotNull();
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);
  }


  @Test
  public void testClintDetail_existID() {

    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

    //
    //
    ClientDetail clientDetail = clientRegister.get().getClientDetailById(clientId);
    //
    //
    assertThat(clientDetail).isNotNull();
  }

  @Test(expectedExceptions = PersistenceException.class)
  public void testSaveClient_emptyToSave() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientToSave toSave = this.randomEntity.get().clientToSaveE();
    toSave.character.id = charm.id;
    //
    //
    ClientRecord clientRecord = clientRegister.get().saveClient(toSave);
    //
    //
  }

  @Test(expectedExceptions = NullPointerException.class)
  public void testSaveClient_NullToSave() {
    //
    //
    ClientRecord clientRecord = clientRegister.get().saveClient(null);
    //
    //
  }

  @Test
  public void testClintDetail_absentId_saveMode() {
    //
    //
    ClientDetail clientDetail = clientRegister.get().getClientDetailById(0);
    //
    //
    assertThat(clientDetail).isNotNull();
    assertThat(clientDetail.characters).isNotNull();
    assertThat(clientDetail.genders).isNotNull();
    assertThat(clientDetail.phoneDetailList).isNotNull();
  }


  @Test
  public void testDeleteClient() {
    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);
    //
    //
    clientRegister.get().deleteClient(clientId);
    //
    //
    ClientDb deletedClient = this.clientTestDao.get().getClientDb(clientId);
    assertThat(deletedClient).isNull();
  }

  @Test
  public void testSaveClient() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientToSave clientToSave = this.randomEntity.get().clientToSave();
    clientToSave.character.id = charm.id;
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(clientToSave);
    //
    //
    assertThat(record).isNotNull();
    assertThat(record.fio).isEqualToIgnoringCase(clientToSave.surname + " " + clientToSave.name + " "
      + clientToSave.patronymic);
  }

  @Test
  public void testUpdateClient() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientDb clientDb = this.randomEntity.get().clientDb(charm.id);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

//    System.out.println("Before update: \nFio is:" + clientDb.surname + " " + clientDb.name +
//      " "+ clientDb.patronymic);

    ClientToSave clientToSave = this.randomEntity.get().clientToSave();
    clientToSave.clientID = clientId;
    clientToSave.character.id = charm.id;
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(clientToSave);
    //
    //

//    System.out.println("After update: \nFio is:" + record.fio);
    assertThat(record).isNotNull();
    assertThat(record.fio).isEqualToIgnoringCase(clientToSave.surname + " " + clientToSave.name + " "
      + clientToSave.patronymic);
  }

  @Test
  public void testSaveAddress() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientToSave clientToSave = this.randomEntity.get().clientToSave();
    clientToSave.character.id = charm.id;
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(clientToSave);
    //
    //
    assertThat(record).isNotNull();
    ClientAddrDb add = clientTestDao.get().getAddress(clientToSave.clientID, "REG");
    assertThat(add).isNotNull();
    add = clientTestDao.get().getAddress(clientToSave.clientID, "FACT");
    assertThat(add).isNotNull();
  }

  @Test
  public void testUpdateAddress() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientDb clientDb = this.randomEntity.get().clientDb(charm.id);
    int clientId = clientTestDao.get().insertClientDb(clientDb);


    ClientToSave clientToSave = this.randomEntity.get().clientToSave();
    clientToSave.clientID = clientId;
    clientToSave.character.id = charm.id;
//    System.out.println("Before update: \nStreet is:" + clientToSave.registrationAddress.street);

    clientTestDao.get().saveOrUpdateClient(clientDb);

    ClientAddrDb rAddress = new ClientAddrDb();
    rAddress = rAddress.getAddressFromToSave(clientToSave, "REG");
    clientTestDao.get().saveOrUpdateAddress(rAddress);
    ClientAddrDb fAddress = new ClientAddrDb();
    fAddress = fAddress.getAddressFromToSave(clientToSave, "FACT");
    clientTestDao.get().saveOrUpdateAddress(fAddress);


    ClientToSave toSave = this.randomEntity.get().clientToSave();
    toSave.clientID = clientId;
    toSave.character.id = charm.id;
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(toSave);
    //
    //
    assertThat(record).isNotNull();
    ClientAddrDb add = clientTestDao.get().getAddress(clientToSave.clientID, "REG");
    assertThat(add).isNotEqualTo(rAddress);
    add = clientTestDao.get().getAddress(clientToSave.clientID, "FACT");
    assertThat(add).isNotEqualTo(fAddress);
//    System.out.println("After update: \nStreet is:" + add.street);
//    assertThat(record.fio).isEqualToIgnoringCase(clientToSave.surname + " " + clientToSave.name + " "
//      + clientToSave.patronymic);

  }

  @Test
  public void testSavePhone() {

    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientDb clientDb = this.randomEntity.get().clientDb(charm.id);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

    ClientToSave toSave = this.randomEntity.get().clientToSave();
    toSave.clientID = clientId;
    toSave.character.id = charm.id;

    clientTestDao.get().saveOrUpdateClient(clientDb);

    for (int i = 0; i < toSave.phones.size(); i++) {
      ClientPhoneDb phoneToSave = this.randomEntity.get().clientPhoneDb(toSave.clientID, toSave.phones.get(i));
      this.clientTestDao.get().saveOrUpdatePhone(phoneToSave);
    }
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(toSave);
    //
    //
    List<ClientPhoneDb> phoneDbChanged = this.clientTestDao.get().getPhoneList(toSave.clientID);//

    assertThat(record).isNotNull();
    assertThat(phoneDbChanged).isNotNull();
    for (int i = 0; i < phoneDbChanged.size(); i++) {
      assertThat(phoneDbChanged.get(i)).isNotNull();
    }

  }

  @Test
  public void testUpdatePhone() {
    CharmDb charm = this.clientTestDao.get().getCharmByName(CharacterType.AGREEABLENESS.toString());
    ClientDb clientDb = this.randomEntity.get().clientDb(charm.id);
    int clientId = clientTestDao.get().insertClientDb(clientDb);


    ClientToSave toSave = this.randomEntity.get().clientToSave();
    toSave.clientID = clientId;
    toSave.character.id = charm.id;
//    System.out.println("Before update: \nStreet is:" + clientToSave.registrationAddress.street);

    clientTestDao.get().saveOrUpdateClient(clientDb);

    List<ClientPhoneDb> phoneListBefore = this.randomEntity.get().clientPhoneDb(toSave);
    for (int i = 0; i < toSave.phones.size(); i++) {

      ClientPhoneDb phoneToSave = this.randomEntity.get().clientPhoneDb(toSave.clientID, toSave.phones.get(i));
      this.clientTestDao.get().saveOrUpdatePhone(phoneToSave);
      toSave.phones.set(i, this.randomEntity.get().phone(phoneToSave.number));
    }
    //
    //
    ClientRecord record = this.clientRegister.get().saveClient(toSave);
    //
    //
    List<ClientPhoneDb> phoneDbChanged = this.clientTestDao.get().getPhoneList(toSave.clientID);//

    assertThat(record).isNotNull();
    assertThat(phoneDbChanged).isNotNull();
    for (int i = 0; i < phoneDbChanged.size(); i++) {
      System.out.println("Before: " + phoneListBefore.get(i).number +
        " After: " + phoneDbChanged.get(i).number);
      assertThat(phoneDbChanged.get(i)).isNotEqualTo(phoneListBefore.get(i));
    }

  }


  @Test
  public void delete() {
    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);


    TransactionTypeDb tType = this.randomEntity.get().transactionTypeDb();
    ClientAccountDb account = this.randomEntity.get().clientAccountDb(clientId);
    int clientAccount = this.clientTestDao.get().insertClientAccountDb(account);
    int tTypeId = this.clientTestDao.get().insertTransactionType(tType);

    ClientAddrDb clientAddrDb = this.randomEntity.get().clientAddrDb(clientId, "REG");
    clientTestDao.get().saveOrUpdateAddress(clientAddrDb);
    clientAddrDb = this.randomEntity.get().clientAddrDb(clientId, "FACT");
    clientTestDao.get().saveOrUpdateAddress(clientAddrDb);

    ClientAccountTransactionDb accountTransaction = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
    int cAccountTDb = this.clientTestDao.get().insertClientAccountTransaction(accountTransaction);
    ClientAddrDb rAddress = this.clientTestDao.get().getAddr(clientId, "REG");
    ClientAddrDb fAddress = this.clientTestDao.get().getAddr(clientId, "FACT");
    List<ClientPhoneDb> phoneDb = this.randomEntity.get().clientPhoneDbList(clientId);

    for (ClientPhoneDb phone : phoneDb) {
      clientTestDao.get().saveOrUpdatePhone(phone);
    }
    this.clientTestDao.get().insertAddress(rAddress);
    this.clientTestDao.get().insertAddress(fAddress);

    //
    //
    this.clientRegister.get().deleteClient(clientId);
    //
    //

    List<ClientPhoneDb> phoneList = this.clientTestDao.get().getPhoneList(clientId);
    assertThat(this.clientTestDao.get().getClientDb(clientId)).isNull();
    assertThat(this.clientTestDao.get().getClientAcc(clientAccount)).isNull();
    assertThat(this.clientTestDao.get().getAccountTransaction(clientAccount)).isNull();
    assertThat(this.clientTestDao.get().getAddressDb(rAddress.client, rAddress.type.toString()));
    assertThat(this.clientTestDao.get().getAddressDb(fAddress.client, fAddress.type.toString()));
    for (ClientPhoneDb phone : phoneList) {
      assertThat(clientTestDao.get().getPhone(phone.client, phone.number)).isNull();
    }
  }

}

