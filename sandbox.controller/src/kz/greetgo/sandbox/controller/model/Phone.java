package kz.greetgo.sandbox.controller.model;

public class Phone {
  public PhoneDetail detail;
  public String number;

  public Phone() {
  }

  public Phone(PhoneDetail detail, String number) {
    this.detail = detail;
    this.number = number;
  }
}
