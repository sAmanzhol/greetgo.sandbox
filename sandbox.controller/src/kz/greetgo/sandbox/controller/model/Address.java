package kz.greetgo.sandbox.controller.model;


import kz.greetgo.sandbox.controller.model.enums.AddressType;

public class Address {
    public Long id;
    public Long clientId;
    public AddressType type;
    public String street;
    public String house;
    public String flat;
    public Boolean actual;

    public Address(Long id, Long clientId, Object type, String street, String house, String flat,Boolean actual) {
        this.id = id;
        this.clientId = clientId;
        this.type = AddressType.valueOf(type.toString());
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.actual = actual;
    }
}
