package kz.greetgo.sandbox.controller.model;


import com.sun.istack.internal.NotNull;
import kz.greetgo.sandbox.controller.model.enums.AddressType;

public class Address {
    public Long id;
    @NotNull
    public Long clientId;
    @NotNull
    public AddressType type;
    @NotNull
    public String street;
    @NotNull
    public String house;
    @NotNull
    public String flat;

    public Address(Long id, Long clientId, Object type, String street, String house, String flat) {
        this.id = id;
        this.clientId = clientId;
        this.type = AddressType.valueOf(type.toString());
        this.street = street;
        this.house = house;
        this.flat = flat;
    }
}
