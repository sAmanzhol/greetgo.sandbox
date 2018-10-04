package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.PhoneTypeRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {

  @Override
  public List<PhoneTypeRecord> list() {
    return null;
  }
}
