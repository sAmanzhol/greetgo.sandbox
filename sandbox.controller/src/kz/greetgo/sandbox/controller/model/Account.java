package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;
import java.util.List;

public class Account {
    public Long id;
    public Float money;
    public String number;
    public Timestamp registered_at;

    public List<AccountTransaction> transactions;

}
