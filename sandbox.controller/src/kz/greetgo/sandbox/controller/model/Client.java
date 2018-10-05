package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.enums.Gender;

import java.sql.Timestamp;
import java.util.List;

public class Client {
    public Long id;
    public String surname;
    public String name;
    public String patronymic;
    public Gender gender;
    public Timestamp birthDate;
    public Charm charm;
    public Boolean actual;

    public Client(Long id, String surname, String name, String patronymic, Object gender, Timestamp birthDate, Charm charm) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = Gender.valueOf(gender.toString());
        this.birthDate = birthDate;
        this.charm = charm;
    }

    public Client(Long id, String surname, String name, String patronymic, Object gender, Timestamp birthDate,
                  Long charm_id,String charm_name,String charm_desc,Double charm_energy)
    {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.gender = Gender.valueOf(gender.toString());
        this.birthDate = birthDate;
        this.charm = new Charm(charm_id,charm_name,charm_desc,charm_energy,actual);
        this.actual = true;
    }

    public List<Address> addresses;
    public List<Phone> phones;
    public List<Account> accounts;
}
