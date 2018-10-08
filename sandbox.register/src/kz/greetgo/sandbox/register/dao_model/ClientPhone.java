package kz.greetgo.sandbox.register.dao_model;

public class ClientPhone {
  public int id;
  public int client;
  public String type;
  public String number;

  public ClientPhone() {

  }

  public ClientPhone(int client, String type, String number) {
    this.client = client;
    this.type = type;
    this.number = number;
  }

  public ClientPhone(int id, int client, String type, String number) {
    this.id = id;
    this.client = client;
    this.type = type;
    this.number = number;
  }
}
