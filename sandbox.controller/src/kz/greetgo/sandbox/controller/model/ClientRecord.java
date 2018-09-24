package kz.greetgo.sandbox.controller.model;

// FIXME: 9/24/18 Приведи все переменные к положенному типу (age is int пр.). То же самое для клиента
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
