package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.AddressOfRegistration;
import kz.greetgo.sandbox.controller.model.AddressOfResidence;
import kz.greetgo.sandbox.controller.model.Phone;

public class Client {
  public int id;
  public String firstname;
  public String lastname;
  public String patronymic;
  public String gender;
  public String dateOfBirth;
  public String character;
  public AddressOfResidence addressOfResidence;
  public AddressOfRegistration addressOfRegistration;
  public Phone phone;
}
