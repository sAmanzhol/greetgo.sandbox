package kz.greetgo.sandbox.controller.model;


/**
 * Created by msultanova on 9/5/18.
 */
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
