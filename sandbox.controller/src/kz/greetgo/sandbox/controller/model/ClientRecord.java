package kz.greetgo.sandbox.controller.model;

public class ClientRecord {
  public String fio;
  public Character character;
  public int age;
  public int totalBalance;
  public int minBalance;
  public int maxBalance;
  public int clientId;

  public ClientRecord() {
  }

  public ClientRecord(String fio, Character character, int age, int totalBalance, int minBalance, int maxBalance, int clientId) {
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.totalBalance = totalBalance;
    this.minBalance = minBalance;
    this.maxBalance = maxBalance;
    this.clientId = clientId;
  }
}
