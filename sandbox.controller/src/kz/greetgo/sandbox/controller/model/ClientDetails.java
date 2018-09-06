package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.GenderType;

public class ClientDetails {
  public int id;
  public String firstname;
  public String lastname;
  public String patronymic;
  public GenderType gender;
  public String dateOfBirth;
  public Charm character;
  public ClientAddr addressOfResidence;
  public ClientAddr addressOfRegistration;
}
