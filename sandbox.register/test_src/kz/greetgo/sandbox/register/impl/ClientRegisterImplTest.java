package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
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

  @Test
  public void testFilter_filter_emptyFilter() {


    RandomEntity randomEntity = this.randomEntity.get();

    Client client = randomEntity.client();
    int id = clientTestDao.get().insertClient(client);

    Client client2 = this.randomEntity.get().client();
    int id2 = clientTestDao.get().insertClient(client2);

    ClientFilter clientFilter = new ClientFilter();
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(2);
    assertThat(clientRecordListWrapper.records).hasSize(clientRecordListWrapper.count);
  }

  @Test
  public void testFilter_filter_columnNameFilter() {

    ClientFilter clientFilter = new ClientFilter();
    clientFilter.columnName = RND.str(4);
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).hasSize(0);
  }

  @Test
  public void testFilter_filter_limit() {

    ClientFilter clientFilter = new ClientFilter();
    clientFilter.limit = 1;
    Client client = randomEntity.get().client();
    int id = clientTestDao.get().insertClient(client);
    Client client2 = randomEntity.get().client();
    int id2 = clientTestDao.get().insertClient(client);
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNotNull();
    assertThat(clientRecordListWrapper.records).hasSize(1);
  }


  //getClientDetailById
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

