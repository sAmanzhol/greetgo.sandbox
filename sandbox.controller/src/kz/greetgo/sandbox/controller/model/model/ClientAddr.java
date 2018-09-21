package kz.greetgo.sandbox.controller.model.model;

public class ClientAddr {
    public Integer client;
    public AddrType type;
    public String street;

    @Override
    public String toString() {

        return "ClientAddr{" +
          "client=" + client +
          ", type=" + type +
          ", street='" + street + '\'' +
          ", house='" + house + '\'' +
          ", flat='" + flat + '\'' +
          '}';
    }

    public String house;
    public String flat;
}
