package kz.greetgo.learn.migration.core;

public class AddressRecord {
    public AddressType type;
    public String street;
    public String house;
    public String flat;

    public AddressRecord( Long client, Object type, String street, String house, String flat, Boolean actual) {
        this.type = AddressType.valueOf(type.toString());
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public AddressRecord(){

    }

}
