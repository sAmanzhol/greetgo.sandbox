package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.List;

// FIXME: 9/24/18 ClientDetails
public class ClientDisplay {
  // FIXME: 9/24/18 id везде должен быть числовым
  public String id;
  public String surname;
  public String name;
  public String patronymic;
  // FIXME: 9/24/18 birthDate должен быть Date
  public String birthDate;
  public String gender;
  public String character;
  public String streetRegistration;
  public String houseRegistration;
  public String apartmentRegistration;
  public String streetResidence;
  public String houseResidence;
  public String apartmentResidence;

  public List<PhoneDisplay> numbers = new ArrayList<>();

  public ClientDisplay() {
  }

  public ClientDisplay(String id, String surname, String name, String patronymic, String birthDate, String gender, String character, String streetRegistration, String houseRegistration, String apartmentRegistration, String streetResidence, String houseResidence, String apartmentResidence, List<PhoneDisplay> numbers) {
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
    this.numbers = numbers;
  }
}
