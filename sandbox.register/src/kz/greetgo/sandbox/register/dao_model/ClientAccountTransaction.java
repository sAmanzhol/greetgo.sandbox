package kz.greetgo.sandbox.register.dao_model;

import java.sql.Timestamp;

public class ClientAccountTransaction {
  public int id;
  public int account;
  public double money;
  public Timestamp finishedAt;
  public int type;
}
