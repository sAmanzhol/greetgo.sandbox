package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.PhoneTypeRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class PhoneRegisterImplTest extends ParentTestNg {

  public BeanGetter<PhoneRegister> phoneRegister;

  @Test
  private void list() {

    //
    //
    List<PhoneTypeRecord> phoneTypeRecords = phoneRegister.get().list();
    //
    //

    assertThat(phoneTypeRecords).hasSize(3);
    assertThat(phoneTypeRecords.get(0).type).isEqualTo("HOME");
    assertThat(phoneTypeRecords.get(1).type).isEqualTo("WORK");
    assertThat(phoneTypeRecords.get(2).type).isEqualTo("MOBILE");
  }
}
