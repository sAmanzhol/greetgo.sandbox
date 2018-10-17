package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;
import java.util.List;

public class Account {
    public Long id;
    public Long client;
    public Double money;
    public String number;
    public Timestamp registered_at;
    public Boolean actual;

    public List<AccountTransaction> transactions;

    public Account(Long id, Long client, Double money, String number, Timestamp registered_at, Boolean actual) {
        this.id = id;
        this.client = client;
        this.money = money;
        this.number = number;
        this.registered_at = registered_at;
        this.actual = actual;
    }

    public Account(){
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", client=" + client +
                ", money=" + money +
                ", number='" + number + '\'' +
                ", registered_at=" + registered_at +
                ", actual=" + actual +
                ", transactions=" + transactions +
                '}';
    }
}
