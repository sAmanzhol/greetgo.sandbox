package kz.greetgo.sandbox.controller.model;

public enum GenderType {
  FEMALE("женский"),
  MALE("мужской");
  public String name;

  GenderType(String name) {
    this.name = name;
  }

  public static GenderType parseOrNull(String strEnum) {
    try {
      return GenderType.valueOf(strEnum);
    } catch (Exception e) {
      return null;
    }
  }
}
