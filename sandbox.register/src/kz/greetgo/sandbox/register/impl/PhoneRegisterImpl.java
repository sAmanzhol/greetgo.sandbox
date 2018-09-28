package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.PhoneTypeRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {

  private List<PhoneTypeRecord> list = new ArrayList<>(Arrays.asList(
    new PhoneTypeRecord(1, "Home"),
    new PhoneTypeRecord(2, "Work"),
    new PhoneTypeRecord(3, "Mobile")
  ));

  @Override
  public List<PhoneTypeRecord> list() {
    return list;
  }
}
