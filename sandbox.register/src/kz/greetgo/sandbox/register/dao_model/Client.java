package kz.greetgo.sandbox.register.dao_model;

import java.util.Date;

public class Client {
  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public String gender;
  public Date birth_date;
  public int charm;

  public int age;
  public float balance = 0;
  public float balanceMax = 0;
  public float balanceMin = 0;
}
