package kz.greetgo.sandbox.controller.model;

import javax.security.auth.login.AccountException;
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

    public List<Address> addresses;
    public List<Phone> phones;
    public List<Account> accounts;
}
