package kz.greetgo.sandbox.controller.model.db;

import kz.greetgo.sandbox.controller.model.PhoneType;

public enum PhoneTypeDb {
  HOME("домашний"),
  WORK("рабочий"),
  MOBILE("мобильный");


  public String name;

  PhoneTypeDb(String name){
     this.name = name;
  }

  public static PhoneTypeDb parseOrNull(String strEnum) {
    try {
      return PhoneTypeDb.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}

