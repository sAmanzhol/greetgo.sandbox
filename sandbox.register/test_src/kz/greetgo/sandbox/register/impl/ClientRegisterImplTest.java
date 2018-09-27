package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.sandbox.register.test.util.test_utils.RandomEntity;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {
  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<RandomEntity> randomEntity;


  public void createRequired() {

    this.clientTestDao.get().insertDefaultCharms();

  }

  //filterClients
  @Test(expectedExceptions = NullPointerException.class)/////кажется так писать не обязательно, если в конце assertThat
  public void testFilter_NullFilter() {
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(null);
    //
    //
  }


//  @Test
//  public void testName() {
//    clientRegister.get().test();
//  }

  public void test() {


    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);

//    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
//    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
//
//    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
//    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);
//
//    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
//    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);


  }

  @Test
  public void testFilter_filter_emptyFilter() {
    this.createRequired();
    CharmDb charm = this.randomEntity.get().charmDb();
    int charmId = clientTestDao.get().insertCharm(charm);

    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
    int clientId = clientTestDao.get().insertClientDb(clientDb);


//    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
//    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
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
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);

    }

    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(clientRecordListWrapper.records.size());
  }

  @Test
  public void testFilter_filter_nameFilter() {


//    CharmDb charm = this.randomEntity.get().charmDb();
//    int charmId = clientTestDao.get().insertCharm(charm);
//
//    ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Madina", 1);
//    int clientId = clientTestDao.get().insertClientDb(clientDb);
//
//
//    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
//    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
//
//    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
//    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);
//
//    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
//    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);
//    System.out.println(clientId + " NAme: " + clientDb.name);
    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.name = "Madina";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);

    }

    assertThat(clientRecordListWrapper.records).hasSize(3);

  }

  @Test
  public void testFilter_filter_patronymicFilter() {


//    CharmDb charm = this.randomEntity.get().charmDb();
//    int charmId = clientTestDao.get().insertCharm(charm);
//
//    ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Abai", 3);
//    int clientId = clientTestDao.get().insertClientDb(clientDb);
//
//
//    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
//    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
//
//    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
//    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);
//
//    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
//    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);
//    System.out.println(clientId + " patronymic: " + clientDb.patronymic);


    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.patronymic = "Abai";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);

    }

    assertThat(clientRecordListWrapper.records).hasSize(1);

  }


  @Test
  public void testFilter_filter_surnameFilter() {

//
//    CharmDb charm = this.randomEntity.get().charmDb();
//    int charmId = clientTestDao.get().insertCharm(charm);
//
//    ClientDb clientDb = this.randomEntity.get().clientDb(charmId, "Asa", 2);
//    int clientId = clientTestDao.get().insertClientDb(clientDb);
//
//
//    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
//    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);
//
//    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
//    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);
//
//    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
//    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);
//    System.out.println(clientId + " surname: " + clientDb.surname);

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.surname = "Asa";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);

    }

    assertThat(clientRecordListWrapper.records).hasSize(1);

  }


  @Test
  public void testFilter_filter_limit() {

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.limit = 1;
    clientFilter.name = "Madina";
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);
  }

  @Test
  public void testFilter_filter_offset() {

    ClientFilter clientFilter = this.randomEntity.get().filterE();
    clientFilter.offset = 2;
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    for (int i = 0; i < clientRecordListWrapper.records.size(); i++) {
      System.out.println(clientRecordListWrapper.records.get(i).clientId);
      System.out.println(clientRecordListWrapper.records.get(i).fio);
      System.out.println("AGE is: " + clientRecordListWrapper.records.get(i).age);

    }
    assertThat(clientRecordListWrapper.records).isNotNull();
//    assertThat(clientRecordListWrapper.records).hasSize(1);
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
    System.out.println(clientDetail.clientId + "\n" + clientDetail.name + "\n" + clientDetail.birthDay);
    assertThat(clientDetail).isNotNull();
  }

  @Test
  public void testClintDetail_absentId() {
    //
    //
    ClientDetail clientDetail = clientRegister.get().getClientDetailById(0);
    //
    //
    assertThat(clientDetail).isNotNull();
  }


  @Test
  public void testDeleteClient() {

//    Client client = randomEntity.get().client();
//    int id = clientTestDao.get().insertClient(client);
    //
    //
//    clientRegister.get().deleteClient(id);
    //
    //
//    client = clientTestDao.get().;


//??????? для каждого параметра создать отдельный метод в дао,
// но каждый тогда не понятно что будет возвр
// ащать если метод правильно отработал
//    assertThat(client).isNull();
  }

  @Test
  public void saveOrUpdateClient() {
//    this.createRequired();


    ClientToSave toSave = this.randomEntity.get().clientToSave();
    int charmId = clientTestDao.get().getCharmByName(toSave.character.type.toString());
    toSave.clientID = 8;
    ClientDb clientDb = this.randomEntity.get().clientDb(toSave, charmId);

    //
    //
    clientTestDao.get().saveOrUpdateClient(clientDb);
    //
    //
    ClientDb clientToPrint = clientTestDao.get().getClientDb(8);

    assertThat(clientToPrint).isNotNull();

  }

  @Test
  public void saveOrUpdateAddress() {
//    this.createRequired();


    ClientToSave toSave = this.randomEntity.get().clientToSave();
    int charmId = clientTestDao.get().getCharmByName(toSave.character.type.toString());
    toSave.clientID = 8;
    ClientDb clientDb = this.randomEntity.get().clientDb(toSave, charmId);

    //
    //
    clientTestDao.get().saveOrUpdateClient(clientDb);

    //
    //
    ClientDb clientToPrint = clientTestDao.get().getClientDb(8);
//    System.out.println("Id is: " + clientToPrint.id + " Name is: " + clientToPrint.name);


    ClientAddrDb clientAddrDb = this.randomEntity.get().clientAddrDb(toSave, "REG");///////////////////////
    clientTestDao.get().saveOrUpdateAddress(clientAddrDb);

    ClientAddrDb address = clientTestDao.get().getAddr(8, "REG");
//    System.out.println("Id is: " + address.client + ", type is: " + address.type +
//      ", Street is: " + clientAddrDb.street);

    assertThat(address).isNotNull();

  }

  @Test
  public void saveOrUpdateFullClient() {
//    this.createRequired();


    ClientToSave toSave = this.randomEntity.get().clientToSave();
    int charmId = clientTestDao.get().getCharmByName(toSave.character.type.toString());
    toSave.clientID = 8;
    ClientDb clientDb = this.randomEntity.get().clientDb(toSave, charmId);

    //
    //
    clientTestDao.get().saveOrUpdateClient(clientDb);

    //
    //
    ClientDb clientToPrint = clientTestDao.get().getClientDb(8);
//    System.out.println("Id is: " + clientToPrint.id + " Name is: " + clientToPrint.name);


    ClientAddrDb clientAddrDb = this.randomEntity.get().clientAddrDb(toSave, "REG");///////////////////////
    clientTestDao.get().saveOrUpdateAddress(clientAddrDb);

    ClientAddrDb address = clientTestDao.get().getAddr(8, "REG");
//    System.out.println("Id is: " + address.client + ", type is: " + address.type +
//      ", Street is: " + clientAddrDb.street);


    List<ClientPhoneDb> phoneDb = this.randomEntity.get().clientPhoneDb(toSave);//
    List<ClientPhoneDb> phoneDbChanged = new ArrayList<>();//

    for (ClientPhoneDb phone : phoneDb) {

      clientTestDao.get().deactualPhone(phone.client, phone.oldNumber);
      clientTestDao.get().saveOrUpdatePhone(phone);

//      System.out.println("Phone Type: " + phone.type + ", Number: " + phone.number +
//        ", Old Number" + phone.oldNumber);
    }


    assertThat(clientToPrint).isNotNull();
    assertThat(address).isNotNull();
    assertThat(phoneDbChanged).isNotNull();

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
    List<ClientPhoneDb> phoneDb = this.randomEntity.get().clientPhoneDb(clientId);//

//    List<ClientPhoneDb> phoneDb = this.clientTestDao.get().getPhoneList(clientId);
    for (ClientPhoneDb phone : phoneDb) {
//      clientTestDao.get().deactualPhone(phone.client, phone.oldNumber);
      clientTestDao.get().saveOrUpdatePhone(phone);
    }
    this.clientTestDao.get().insertAddress(rAddress);
    this.clientTestDao.get().insertAddress(fAddress);

    //
    //


//    this.clientTestDao.get().deactualClient(clientId);
//    List<Integer> accounts = this.clientTestDao.get().deactualAccounts(clientId);
//    for (Integer acc : accounts) {
//      this.clientTestDao.get().deactualTransactions(acc);
//    }
//    this.clientTestDao.get().deactualAddress(clientId, "REG");
//    this.clientTestDao.get().deactualAddress(clientId, "FACT");
//    for (ClientPhoneDb phone : phoneDb) {
//      this.clientTestDao.get().deactualPhone(phone.client, phone.number);
//    }



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


  //saveClient
  @Test
  public void testSaveClient_emptyToSave() {

    ClientToSave toSave = new ClientToSave();
    //
    //
    ClientRecord clientRecord = clientRegister.get().saveClient(toSave);
    //
    //
    assertThat(clientRecord).isNull();
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
  public void testSaveClient_ToSave() {

    ClientToSave toSave = new ClientToSave();
//    toSave.name =
    //
    //
    ClientRecord clientRecord = clientRegister.get().saveClient(null);
    //
    //
  }
}

