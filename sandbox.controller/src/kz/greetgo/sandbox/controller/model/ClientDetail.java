package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by msultanova on 9/5/18.
 */
public class ClientDetail {
    public String surname;
    public String name;
    public String patronymic;
    public Gender gender;
    public List<Gender> genders;
    public String birthDay;
    public Character character;
    public List<Character> characters;
    public Address actualAddress;
    public Address registrationAddress;
    public List<Phone> phones = new ArrayList<>();
    public long clientId;

    public ClientDetail() {
    }

    public ClientDetail(String surname, String name, String patronymic, Gender gender, List<Gender> genders, String birthDay, Character character, List<Character> characters, Address actualAddress, Address registrationAddress, List<Phone> phones, long clientId) {

        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = gender;
        this.genders = genders;
        this.birthDay = birthDay;
        this.character = character;
        this.characters = characters;
        this.actualAddress = actualAddress;
        this.registrationAddress = registrationAddress;
        this.phones = phones;
        this.clientId = clientId;
    }
}
