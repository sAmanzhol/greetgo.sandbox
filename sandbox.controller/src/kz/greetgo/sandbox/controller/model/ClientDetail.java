package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.List;
import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.CharacterType;
import kz.greetgo.sandbox.controller.model.Phone;

/**
 * Created by msultanova on 9/5/18.
 */
public class ClientDetail {
  public int detailId;
  public String surname;
  public String name;
  public String patronymic;
  public CharacterType character;
  public Address actualAddress;
  public Address registrationAddress;
  public List<Phone> phones = new ArrayList<>();

  //The following code would be not removed after regenerating
  ///LEAVE_FURTHER
}
