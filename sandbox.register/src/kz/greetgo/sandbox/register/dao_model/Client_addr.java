package kz.greetgo.sandbox.register.dao_model;

public class Client_addr {
  public int client;
  public String type;
  public String street;
  public String house;
  public String flat;

  public Client_addr() {

  }

  public Client_addr(int client, String type, String street, String house, String flat) {
    this.client = client;
    this.type = type;
    this.street = street;
    this.house = house;
    this.flat = flat;
  }
}
