package kz.greetgo.sandbox.register.impl.jdbc.migration.model;

import java.util.List;

public class CiaClient {
  public String id;

  public String surname;
  public String name;
  public String patronymic;
  public String gender;
  public String charm;
  public String birth;

  public List<CiaAddress> addresses;

  public List<CiaPhone> phones;
}
