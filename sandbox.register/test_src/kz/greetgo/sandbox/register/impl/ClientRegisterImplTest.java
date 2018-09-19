package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.RandomEntity;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest {
  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<ClientTestDao> clientTestDao;
  public BeanGetter<RandomEntity> rndObject;

  @Test
  public void testClientList() {

    Client client = rndObject.get().client();
    int id = clientTestDao.get().insertClient(client);
    //
    //
    ArrayList<ClientRecord> clientRecordList = (ArrayList<ClientRecord>) clientRegister.get().getClientList();
    //
    //
    assertThat(clientRecordList).isNotNull();
    assertThat(clientRecordList).hasSize(2);
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


  @Test
  public void testDeleteClient() {

    Client client = rndObject.get().client();
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

  @Test(expectedExceptions = NullPointerException.class)/////кажется так писать не обязательно, если в конце assertThat
  public void testFilter_NullFilter() {
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(null);
    //
    //
    //assertThat(clientRecordListWrapper).isNull();// ошибка не в том, что отправляется, а в том, что принимается
  }
  //    clientRegister.get().saveClient()

  @Test
  public void testFilter_getWrapper_emptyFilter() {


    Client client = rndObject.get().client();
    int id = clientTestDao.get().insertClient(client);


    Client client2 = rndObject.get().client();
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
  public void testFilter_getWrapper_columnNameFilter() {

    ClientFilter clientFilter = new ClientFilter();
    clientFilter.columnName = RND.str(4);
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNull();
    assertThat(clientRecordListWrapper.records).hasSize(0);
  }

  @Test
  public void testFilter_getWrapper_limitFilter() {

    ClientFilter clientFilter = new ClientFilter();
    clientFilter.limit = 0;
    //
    //
    ClientRecordListWrapper clientRecordListWrapper = clientRegister.get().filterClients(clientFilter);
    //
    //
    assertThat(clientRecordListWrapper.records).isNull();
    assertThat(clientRecordListWrapper.records).hasSize(0);
  }

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

