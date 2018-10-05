package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;
import java.util.List;

public class Account {
    public Long id;
    public Float money;
    public String number;
    public Timestamp registered_at;
    public Boolean actual;

    public List<AccountTransaction> transactions;

    public Account(Long id, Float money, String number, Timestamp registered_at, boolean actual,List<AccountTransaction> transactions) {
        this.id = id;
        this.money = money;
        this.number = number;
        this.registered_at = registered_at;
        this.actual = actual;
        this.transactions = transactions;
    }
}
