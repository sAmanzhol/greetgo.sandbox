package kz.greetgo.sandbox.controller.model;

public class Gender {
  public GenderType type;
  public String typeRuss;

  public Gender() {
  }

  public Gender(GenderType type, String typeRuss) {
    this.type = type;
    this.typeRuss = typeRuss;
  }
}
