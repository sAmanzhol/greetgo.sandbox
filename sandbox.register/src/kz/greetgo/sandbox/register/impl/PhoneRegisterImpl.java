package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.PhoneRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {

  private List<PhoneRecord> list = new ArrayList<>(Arrays.asList(
    new PhoneRecord("1", "Home"),
    new PhoneRecord("2", "Work"),
    new PhoneRecord("3", "Mobile")
  ));

  @Override
  public List<PhoneRecord> list() {
    return list;
  }
}
