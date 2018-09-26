package kz.greetgo.sandbox.controller.model.db;

public enum AddressTypeDb {
  FACT,
  REG;
  public static AddressTypeDb parseOrNull(String strEnum) {
    try {
      return AddressTypeDb.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}
