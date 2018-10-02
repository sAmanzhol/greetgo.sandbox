package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.db.PhoneTypeDb;

public class PhoneDetail {
  public PhoneTypeDb type;
  public String typeRuss;

  public PhoneDetail() {
  }

  public PhoneDetail(PhoneTypeDb type, String typeRuss) {
    this.type = type;
    this.typeRuss = typeRuss;
  }
}
