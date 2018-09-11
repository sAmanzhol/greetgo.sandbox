package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by msultanova on 9/6/18.
 */
public class Client {
    public long id;
    public String surname;
    public String name;
    public String patronymic;
    public Gender gender;
    public Date birthDay;
    public Character character;
    public int age;
    public int totalBalance;
    public int minBalance;
    public int maxBalance;
    public Address actualAddress;
    public Address registrationAddress;
    public List<Phone> phones = new ArrayList<>();

    public Client() {
    }

    public Client(long id, String surname, String name, String patronymic, Gender gender, Date birthDay, Character character, int age, int totalBalance, int minBalance, int maxBalance, Address actualAddress, Address registrationAddress, List<Phone> phones) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = gender;
        this.birthDay = birthDay;
        this.character = character;
        this.age = age;
        this.totalBalance = totalBalance;
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
        this.actualAddress = actualAddress;
        this.registrationAddress = registrationAddress;
        this.phones = phones;
    }

}
