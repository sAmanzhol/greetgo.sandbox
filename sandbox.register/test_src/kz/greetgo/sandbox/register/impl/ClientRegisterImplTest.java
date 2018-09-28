package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.model.ClientToFilter;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.List;

public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientRegister> clientRegister;

  @Test
  public void list_sortBy_fio_asc() {
    ClientToFilter filter = new ClientToFilter("fio", "asc", "", 1, 10);
    List<ClientRecord> list = clientRegister.get().list(filter);
  }

  @Test
  public void list_sortBy_fio_desc() {

  }

  @Test
  public void list_sortBy_age_asc() {

  }

  @Test
  public void list_sortBy_age_desc() {

  }

  @Test
  public void list_sortBy_balance_asc() {

  }

  @Test
  public void list_sortBy_balance_desc() {

  }

  @Test
  public void list_sortBy_balanceMax_asc() {

  }

  @Test
  public void list_sortBy_balanceMax_desc() {

  }

  @Test
  public void list_sortBy_balanceMin_asc() {

  }

  @Test
  public void list_sortBy_balanceMin_desc() {

  }
}
