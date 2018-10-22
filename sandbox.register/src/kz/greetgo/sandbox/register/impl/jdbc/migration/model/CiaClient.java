package kz.greetgo.sandbox.register.impl.jdbc.migration.model;

import java.util.ArrayList;
import java.util.List;

public class CiaClient {

  public String id;
  public String surname;
  public String name;
  public String patronymic;
  public String gender;
  public String charm;
  public String birthDate;

  public List<CiaAddress> addresses;

  public List<CiaPhone> phones;

  public CiaClient() {
    id = "";
    surname = "";
    name = "";
    patronymic = "";
    gender = "";
    charm = "";
    birthDate = "";

    addresses = new ArrayList<>();
    phones = new ArrayList<>();
  }
}
