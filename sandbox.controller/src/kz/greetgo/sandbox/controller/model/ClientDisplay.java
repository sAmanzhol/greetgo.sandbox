package kz.greetgo.sandbox.controller.model;

public class ClientDisplay {
  public String id;
  public String surname;
  public String name;
  public String patronymic;
  public String birthDate;
  public String gender;
  public String character;
  public String streetRegistration;
  public String houseRegistration;
  public String apartmentRegistration;
  public String streetResidence;
  public String houseResidence;
  public String apartmentResidence;
  public String phoneHome;
  public String phoneWork;
  public String phoneExtra3;
  public String phoneExtra4;
  public String phoneExtra5;

  public ClientDisplay() {
  }

  public ClientDisplay(String id, String surname, String name, String patronymic, String birthDate, String gender, String character, String streetRegistration, String houseRegistration, String apartmentRegistration, String streetResidence, String houseResidence, String apartmentResidence, String phoneHome, String phoneWork, String phoneExtra3, String phoneExtra4, String phoneExtra5) {
    this.id = id;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.birthDate = birthDate;
    this.gender = gender;
    this.character = character;
    this.streetRegistration = streetRegistration;
    this.houseRegistration = houseRegistration;
    this.apartmentRegistration = apartmentRegistration;
    this.streetResidence = streetResidence;
    this.houseResidence = houseResidence;
    this.apartmentResidence = apartmentResidence;
    this.phoneHome = phoneHome;
    this.phoneWork = phoneWork;
    this.phoneExtra3 = phoneExtra3;
    this.phoneExtra4 = phoneExtra4;
    this.phoneExtra5 = phoneExtra5;
  }

  @Override
  public String toString() {
    return "ClientDisplay{" +
      "id='" + id + '\'' +
      ", surname='" + surname + '\'' +
      ", name='" + name + '\'' +
      ", patronymic='" + patronymic + '\'' +
      ", birthDate='" + birthDate + '\'' +
      ", gender='" + gender + '\'' +
      ", character='" + character + '\'' +
      ", streetRegistration='" + streetRegistration + '\'' +
      ", houseRegistration='" + houseRegistration + '\'' +
      ", apartmentRegistration='" + apartmentRegistration + '\'' +
      ", streetResidence='" + streetResidence + '\'' +
      ", houseResidence='" + houseResidence + '\'' +
      ", apartmentResidence='" + apartmentResidence + '\'' +
      ", phoneHome='" + phoneHome + '\'' +
      ", phoneWork='" + phoneWork + '\'' +
      ", phoneExtra3='" + phoneExtra3 + '\'' +
      ", phoneExtra4='" + phoneExtra4 + '\'' +
      ", phoneExtra5='" + phoneExtra5 + '\'' +
      '}';
  }
}
