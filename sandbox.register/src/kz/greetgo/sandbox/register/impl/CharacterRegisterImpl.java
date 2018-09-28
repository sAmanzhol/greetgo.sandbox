package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.CharacterRecord;
import kz.greetgo.sandbox.controller.register.CharacterRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Bean
public class CharacterRegisterImpl implements CharacterRegister {
  private List<CharacterRecord> list = new ArrayList<>(Arrays.asList(
    new CharacterRecord(1, "Самовлюблённый"),
    new CharacterRecord(2, "Замкнутый"),
    new CharacterRecord(3, "Великодушный"),
    new CharacterRecord(4, "Бессердечный"),
    new CharacterRecord(5, "Грубый"),
    new CharacterRecord(6, "Целеустремлённый"),
    new CharacterRecord(7, "Мизантроп"),
    new CharacterRecord(8, "Строгий"),
    new CharacterRecord(8, "Строгий"),
    new CharacterRecord(9, "Гениальный"),
    new CharacterRecord(10, "Харизматичный"),
    new CharacterRecord(11, "Безответственный")
  ));

  @Override
  public List<CharacterRecord> list() {
    return list;
  }
}
