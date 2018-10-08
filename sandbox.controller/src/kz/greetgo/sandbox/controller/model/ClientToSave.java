package kz.greetgo.sandbox.controller.model;

import java.util.*;

public class ClientToSave {
  // FIXME: 10/8/18 почему айди стринг?!
  public Integer id;
  public String surname;
  public String name;
  public String patronymic;
  public Date birthDate;
  public String gender;
  public int characterId;
  public String streetRegistration;
  public String houseRegistration;
  public String apartmentRegistration;
  public String streetResidence;
  public String houseResidence;
  public String apartmentResidence;

  public List<PhoneDisplay> numbers = new ArrayList<>();
  public Map<String, List<PhoneDisplay>> numbersChange = new HashMap<>();

  public ClientToSave() {
  }

  public ClientToSave(Integer id, String surname, String name, String patronymic, Date birthDate, String gender, int characterId, String streetRegistration, String houseRegistration, String apartmentRegistration, String streetResidence, String houseResidence, String apartmentResidence, List<PhoneDisplay> numbers) {
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
    this.numbersChange.put("created", new ArrayList<>());
    this.numbersChange.put("updated", new ArrayList<>());
    this.numbersChange.put("deleted", new ArrayList<>());
  }
}
