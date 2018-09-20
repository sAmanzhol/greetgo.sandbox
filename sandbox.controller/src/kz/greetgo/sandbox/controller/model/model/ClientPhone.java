package kz.greetgo.sandbox.controller.model.model;



public class ClientPhone {
    public int client;
    public PhoneType type;
    public String number;

    @Override
    public String toString() {

        return "ClientPhone{" +
          "client=" + client +
          ", type=" + type +
          ", number='" + number + '\'' +
          '}';
    }
}
