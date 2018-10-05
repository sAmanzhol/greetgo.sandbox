package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;
import java.util.function.LongFunction;

public class AccountTransaction {
    public Long id;
    public Float money;
    public Timestamp finished_at;
    public TransactionType type;

    public Boolean actual;
}
