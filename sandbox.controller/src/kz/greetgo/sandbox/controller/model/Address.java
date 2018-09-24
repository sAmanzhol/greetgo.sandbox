package kz.greetgo.sandbox.controller.model;

public class Address {
  public String street;
  public String house;
  public String flat;

  public Address() {
  }

  public Address(String street, String house, String flat) {
    this.street = street;
    this.house = house;
    this.flat = flat;
  }

  public static Address empty() {
    return new Address("", "", "");
  }
}
