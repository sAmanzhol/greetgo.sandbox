package kz.greetgo.sandbox.controller.model.model;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ClientDetails {
    public int id;
    public String firstname;
    public String lastname;
    public String patronymic;
    public GenderType gender;
    public String dateOfBirth;
    public Charm character;
    public ClientAddr addressOfResidence;
    public ClientAddr addressOfRegistration;
    public List<ClientPhone> phone;

    public ClientDetails() {
        this.id = 0;
        this.firstname = "";
        this.lastname = "";
        this.patronymic = "";
        this.gender = GenderType.MALE;
        this.dateOfBirth = "2018-08-22";
        this.character = new Charm();
        this.addressOfRegistration = new ClientAddr();
        this.addressOfRegistration.type = AddrType.REG;
        this.addressOfResidence = new ClientAddr();
        this.addressOfResidence.type = AddrType.FACT;
        this.phone = new ArrayList<>();
    }
}
