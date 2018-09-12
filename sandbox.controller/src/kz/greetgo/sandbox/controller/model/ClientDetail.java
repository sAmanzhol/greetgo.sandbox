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
    public int age;
    public Gender gender;
    public List<Gender> genders;
    public Date birthDay;
    public Character character;
    public List<Character> characters;
    public Address actualAddress;
    public Address registrationAddress;
    public List<Phone> phones;
    public List<PhoneDetail> phoneDetailList;
    public long clientId;


    public ClientDetail() {
    }

    public static ClientDetail forSave(List<Gender> genders, List<Character> characters, List<PhoneDetail> phoneDetailList) {
        return new ClientDetail("", "", "", 0, new Gender(), genders, null,
                new Character(), characters, Address.empty(), Address.empty(), new ArrayList<>(), phoneDetailList, -1);
    }

    public ClientDetail initalize(ClientDetail clientDetail) {
        this.surname = clientDetail.surname;
        this.name = clientDetail.name;
        this.patronymic = clientDetail.patronymic;
        this.age = clientDetail.age;
        this.gender = clientDetail.gender;
        this.birthDay = clientDetail.birthDay;
        this.character = clientDetail.character;
        this.actualAddress = clientDetail.actualAddress;
        this.registrationAddress = clientDetail.registrationAddress;
        this.phones = clientDetail.phones;
        this.clientId = clientDetail.clientId;
        return this;
    }

    public ClientDetail(String surname, String name, String patronymic, int age, Gender gender, List<Gender> genders, Date birthDay,
                        Character character, List<Character> characters, Address actualAddress, Address registrationAddress,
                        List<Phone> phones, List<PhoneDetail> phoneDetailList, long clientId) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.age = age;
        this.gender = gender;
        this.genders = genders;
        this.birthDay = birthDay;
        this.character = character;
        this.characters = characters;
        this.actualAddress = actualAddress;
        this.registrationAddress = registrationAddress;
        this.phones = phones;
        this.phoneDetailList = phoneDetailList;
        this.clientId = clientId;
    }
}
