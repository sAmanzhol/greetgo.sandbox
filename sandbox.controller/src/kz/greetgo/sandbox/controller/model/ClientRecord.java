package kz.greetgo.sandbox.controller.model;

// FIXME: 9/24/18 Приведи все переменные к положенному типу (age is int пр.). То же самое для клиента
public class ClientRecord {
  public int id;
  public String fio;
  public String character;
  public int age;
  public int balance;
  public int balanceMax;
  public int balanceMin;

  public ClientRecord() {

  }

  public ClientRecord(int id, String fio, String character, int age, int balance, int balanceMax, int balanceMin) {
    this.id = id;
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.balance = balance;
    this.balanceMax = balanceMax;
    this.balanceMin = balanceMin;
  }
}
