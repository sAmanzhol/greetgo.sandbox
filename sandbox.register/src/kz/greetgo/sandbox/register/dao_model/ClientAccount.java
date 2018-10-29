package kz.greetgo.sandbox.register.dao_model;

import java.sql.Timestamp;

public class ClientAccount {
  public int id;
  public Integer client;
  public double money;
  public String number;
  public Timestamp registeredAt;

  public int actual;
  public String migrationClient;
}
