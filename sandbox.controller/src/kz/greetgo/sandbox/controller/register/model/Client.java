package kz.greetgo.sandbox.controller.register.model;

import kz.greetgo.sandbox.controller.model.model.Charm;
import kz.greetgo.sandbox.controller.model.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.model.ClientPhone;
import kz.greetgo.sandbox.controller.model.model.GenderType;

import java.util.List;

public class Client {
    public int id;
    public String firstname;
    public String lastname;
    public String patronymic;
    public Charm character;
    public String dateOfBirth;
    public int totalAccountBalance;
    public int maximumBalance;
    public int minimumBalance;
    public GenderType gender;
    public ClientAddr addressOfResidence;
    public ClientAddr addressOfRegistration;
    public List<ClientPhone> phone;
    public Client(){
        character= new Charm();
    }

}
