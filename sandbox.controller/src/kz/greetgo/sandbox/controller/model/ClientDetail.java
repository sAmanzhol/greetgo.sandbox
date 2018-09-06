package kz.greetgo.sandbox.controller.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by msultanova on 9/5/18.
 */
public class ClientDetail {
    public String surname;
    public String name;
    public String patronymic;
    public CharacterType character;
    public Address actualAddress;
    public Address registrationAddress;
    public List<Phone> phones = new ArrayList<>();
    public long clientId;

    public ClientDetail(String surname, String name, String patronymic, CharacterType character, Address actualAddress, Address registrationAddress, List<Phone> phones, long clientId) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.character = character;
        this.actualAddress = actualAddress;
        this.registrationAddress = registrationAddress;
        this.phones = phones;
        this.clientId = clientId;
    }
}
