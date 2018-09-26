package kz.greetgo.sandbox.controller.model;

public class Phone {
  public PhoneDetail detail;
  public String number;
  public String oldNumber;

  public Phone() {
  }

  public Phone(PhoneDetail detail, String number) {
    this.detail = detail;
    this.number = number;
    this.oldNumber = number;
  }

  public Phone(PhoneDetail detail, String number, String oldNumber) {
    this.detail = detail;
    this.number = number;
    this.oldNumber = oldNumber;
  }
}
