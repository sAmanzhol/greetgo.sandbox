package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.CharacterRecord;
import kz.greetgo.sandbox.controller.register.CharacterRegister;
import kz.greetgo.sandbox.register.dao.CharacterDao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class CharacterRegisterImpl implements CharacterRegister {

  public BeanGetter<CharacterDao> characterDao;

  @Override
  public List<CharacterRecord> list() {
    return characterDao.get().list();
  }
}
