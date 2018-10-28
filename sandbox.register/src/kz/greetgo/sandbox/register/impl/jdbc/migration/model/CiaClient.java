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

  public CiaClient() {
    id = "";
    surname = "";
    name = "";
    patronymic = "";
    gender = "";
    charm = "";
    birthDate = "";
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getCharm() {
    return charm;
  }

  public void setCharm(String charm) {
    this.charm = charm;
  }

  public String getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  @Override
  public String toString() {
    return "CiaClient{" +
      "id='" + id + '\'' +
      ", surname='" + surname + '\'' +
      ", name='" + name + '\'' +
      ", patronymic='" + patronymic + '\'' +
      ", gender='" + gender + '\'' +
      ", charm='" + charm + '\'' +
      ", birthDate='" + birthDate + '\'' +
      '}';
  }
}
