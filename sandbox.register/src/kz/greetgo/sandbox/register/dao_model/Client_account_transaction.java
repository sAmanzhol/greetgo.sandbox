package kz.greetgo.sandbox.register.dao_model;

import java.sql.Timestamp;

public class Client_account_transaction {
  public int id;
  public int account;
  public float money;
  public Timestamp finished_at;
  public int type;
}
