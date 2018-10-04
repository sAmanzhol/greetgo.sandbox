package kz.greetgo.sandbox.register.dao_model;

public class Client_phone {
  public int id;
  public int client;
  public String type;
  public String number;

  public Client_phone() {

  }

  public Client_phone(int client, String type, String number) {
    this.client = client;
    this.type = type;
    this.number = number;
  }

  public Client_phone(int id, int client, String type, String number) {
    this.id = id;
    this.client = client;
    this.type = type;
    this.number = number;
  }
}
