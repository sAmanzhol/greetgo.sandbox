package kz.greetgo.sandbox.controller.model;

public class ClientRecord {
  public int id;
  public String fio;
  public String character;
  public int age;
  public double balance;
  public double balanceMax;
  public double balanceMin;

  public ClientRecord() {

  }

  public ClientRecord(int id, String fio, String character, int age, float balance, float balanceMax, float balanceMin) {
    this.id = id;
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.balance = balance;
    this.balanceMax = balanceMax;
    this.balanceMin = balanceMin;
  }
}
