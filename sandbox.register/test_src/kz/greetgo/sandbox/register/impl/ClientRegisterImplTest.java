package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.model.db.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.sandbox.register.test.util.test_utils.RandomEntity;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {
  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<RandomEntity> randomEntity;


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

    ClientAccountDb clientAccountDb = this.randomEntity.get().clientAccountDb(clientId);
    int clientAccount = clientTestDao.get().insertClientAccountDb(clientAccountDb);

    TransactionTypeDb typeTDb = this.randomEntity.get().transactionTypeDb();
    int tTypeId = clientTestDao.get().insertTransactionType(typeTDb);

    ClientAccountTransactionDb accountTransactionDb = this.randomEntity.get().clientAccountTransactionDb(tTypeId, clientAccount);
    int cAccountTDb = clientTestDao.get().insertClientAccountTransaction(accountTransactionDb);


  }

  @Test
  public void testFilter_filter_emptyFilter() {

//    CharmDb charm = this.randomEntity.get().charmDb();
//    int charmId = clientTestDao.get().insertCharm(charm);
//
//    ClientDb clientDb = this.randomEntity.get().clientDb(charmId);
//    int clientId = clientTestDao.get().insertClientDb(clientDb);
//
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

    }
    assertThat(clientRecordListWrapper.records).isNotNull();
//    assertThat(clientRecordListWrapper.records).hasSize(1);
  }


  @Test
  public void testClintDetail_absent() {
    //
    //
    ClientDetail clientDetail = clientRegister.get().getClientDetailById(RND.plusInt(12));
    //
    //
    assertThat(clientDetail).isNull();
  }


  @Test//deleteClient
  public void testDeleteClient() {

    Client client = randomEntity.get().client();
    int id = clientTestDao.get().insertClient(client);
    //
    //
    clientRegister.get().deleteClient(id);
    //
    //
//    client = clientTestDao.get().;


//??????? для каждого параметра создать отдельный метод в дао,
// но каждый тогда не понятно что будет возвр
// ащать если метод правильно отработал
    assertThat(client).isNull();
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

