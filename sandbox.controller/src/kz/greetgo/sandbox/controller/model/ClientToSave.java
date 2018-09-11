package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msultanova on 9/10/18.
 */
public class ClientToSave {
 public long clientID;
 public String surname;
 public String name;
 public String patronymic;
 public Gender gender;
 public String birthDay;
 public Character character;
 public Address actualAddress;
 public Address registrationAddress;
 public List<Phone> phones = new ArrayList<>();

}
