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

  public Client() {

  }

  public Client(String surname, String name, String patronymic, String gender, Date birth_date, int charm) {
    this.id = 0;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.gender = gender;
    this.birth_date = birth_date;
    this.charm = charm;
  }

  public Client(int id, String surname, String name, String patronymic, String gender, Date birth_date, int charm) {
    this.id = id;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.gender = gender;
    this.birth_date = birth_date;
    this.charm = charm;
  }
}
