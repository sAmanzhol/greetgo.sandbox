package kz.greetgo.sandbox.controller.model;

public class PhoneDisplay {
  public int id;
  public String type;
  public String number;

  public PhoneDisplay() {
    this.id = 0;
    this.type = "HOME";
  }

  public PhoneDisplay(int id, String type, String number) {
    this.id = id;
    this.type = type;
    this.number = number;
  }
}
