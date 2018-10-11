package kz.greetgo.sandbox.controller.model;


import kz.greetgo.sandbox.controller.model.enums.AddressType;

public class Address {
    public Long id;
    public Long client;
    public AddressType type;
    public String street;
    public String house;
    public String flat;
    public Boolean actual;

    public Address(Long id, Long client, Object type, String street, String house, String flat, Boolean actual) {
        this.id = id;
        this.client = client;
        this.type = AddressType.valueOf(type.toString());
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.actual = actual;
    }
    public Address(){

    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", client=" + client +
                ", type=" + type +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", flat='" + flat + '\'' +
                ", actual=" + actual +
                '}';
    }
}
