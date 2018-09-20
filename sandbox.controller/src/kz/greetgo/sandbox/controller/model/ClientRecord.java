package kz.greetgo.sandbox.controller.model;

public class ClientRecord {
  public String id;
  public String fio;
  public String character;
  public String age;
  public String balance;
  public String balanceMax;
  public String balanceMin;

  public ClientRecord(String id, String fio, String character, String age, String balance, String balanceMax, String balanceMin) {
    this.id = id;
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.balance = balance;
    this.balanceMax = balanceMax;
    this.balanceMin = balanceMin;
  }
}
