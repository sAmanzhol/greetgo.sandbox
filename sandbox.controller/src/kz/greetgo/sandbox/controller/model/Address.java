package kz.greetgo.sandbox.controller.model;

public class Address {
  public String street;
  public String house;
  public String apartment;

  public Address() {
  }

  public Address(String street, String house, String apartment) {
    this.street = street;
    this.house = house;
    this.apartment = apartment;
  }

  public static Address empty() {
    return new Address("", "", "");
  }
}
