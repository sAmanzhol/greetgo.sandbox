package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClientToSave {
  public long clientID;
  public String surname;
  public String name;
  public int age;
  public String patronymic;
  public Gender gender;
  public Date birthDay;
  public Character character;
  public Address actualAddress;
  public Address registrationAddress;
  public List<Phone> phones = new ArrayList<>();
}