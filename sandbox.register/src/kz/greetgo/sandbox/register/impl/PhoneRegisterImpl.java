package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.PhoneTypeRecord;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.dao.PhoneDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {

  public BeanGetter<PhoneDao> phoneDao;

  @Override
  public List<PhoneTypeRecord> list() {
    return phoneDao.get().list();
  }
}
