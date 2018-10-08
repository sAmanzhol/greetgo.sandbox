package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// FIXME: 9/24/18 ClientDetails
public class ClientDisplay {
  public int id;
  public String surname;
  public String name;
  public String patronymic;
  public Date birthDate;
  public int characterId;
  public String gender;
  public String streetRegistration;
  public String houseRegistration;
  public String apartmentRegistration;
  public String streetResidence;
  public String houseResidence;
  public String apartmentResidence;

  public List<PhoneDisplay> numbers = new ArrayList<>();

  public ClientDisplay() { }

  public ClientDisplay(int id, String surname, String name, String patronymic, Date birthDate, String gender, int characterId, String streetRegistration, String houseRegistration, String apartmentRegistration, String streetResidence, String houseResidence, String apartmentResidence, List<PhoneDisplay> numbers) {
    this.id = id;
    this.surname = surname;
    this.name = name;
    this.patronymic = patronymic;
    this.birthDate = birthDate;
    this.gender = gender;
    this.characterId = characterId;
    this.streetRegistration = streetRegistration;
    this.houseRegistration = houseRegistration;
    this.apartmentRegistration = apartmentRegistration;
    this.streetResidence = streetResidence;
    this.houseResidence = houseResidence;
    this.apartmentResidence = apartmentResidence;
    this.numbers = numbers;
  }
}
