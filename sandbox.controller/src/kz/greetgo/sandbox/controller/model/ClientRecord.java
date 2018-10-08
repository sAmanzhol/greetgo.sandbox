package kz.greetgo.sandbox.controller.model;

// FIXME: 9/24/18 Приведи все переменные к положенному типу (age is int пр.). То же самое для клиента
public class ClientRecord {
  public int id;
  public String fio;
  public String character;
  public int age;
  public float balance;
  public float balanceMax;
  public float balanceMin;

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
