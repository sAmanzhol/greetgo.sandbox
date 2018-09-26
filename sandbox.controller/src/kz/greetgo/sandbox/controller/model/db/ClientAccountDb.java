package kz.greetgo.sandbox.controller.model.db;

import java.sql.Timestamp;

public class ClientAccountDb {
  public int id;
  public int client;
  public float money;
  public String number;
  public Timestamp registeredAt;
  public boolean actual = true;


}
