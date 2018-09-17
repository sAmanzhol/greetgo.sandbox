package kz.greetgo.sandbox.controller.model;

public class PhoneDetail {
  public PhoneType type;
  public String typeRuss;

  public PhoneDetail() {
  }

  public PhoneDetail(PhoneType type, String typeRuss) {
    this.type = type;
    this.typeRuss = typeRuss;
  }
}
