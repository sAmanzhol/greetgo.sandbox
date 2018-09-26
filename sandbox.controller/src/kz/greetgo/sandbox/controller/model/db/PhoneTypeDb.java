package kz.greetgo.sandbox.controller.model.db;

public enum PhoneTypeDb {
  HOME,
  WORK,
  MOBILE;


  public static PhoneTypeDb parseOrNull(String strEnum) {
    try {
      return PhoneTypeDb.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}
