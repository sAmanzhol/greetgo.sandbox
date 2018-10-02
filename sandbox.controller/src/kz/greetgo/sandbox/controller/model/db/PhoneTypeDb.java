package kz.greetgo.sandbox.controller.model.db;

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

