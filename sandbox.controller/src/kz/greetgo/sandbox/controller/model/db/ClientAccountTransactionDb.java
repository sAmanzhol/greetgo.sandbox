package kz.greetgo.sandbox.controller.model.db;

import java.sql.Timestamp;

public class ClientAccountTransactionDb {

  public int id;
  public int account;
  public float money;
  public Timestamp finishedAt;
  public int type;
  public boolean actual = true;

}
