package kz.greetgo.sandbox.controller.model.db;

import kz.greetgo.sandbox.controller.model.ClientToSave;

public class ClientAddrDb {
  public int client;
  public AddressTypeDb type;
  public String street;
  public String house;
  public String flat;
  public boolean actual = true;

  public ClientAddrDb getAddressFromToSave(ClientToSave toSave, String type) {
    this.client = toSave.clientID;
    if (AddressTypeDb.parseOrNull(type) != null) {
      this.type = AddressTypeDb.parseOrNull(type);
    }
    if ("FACT".equals(type)) {
      this.street = toSave.actualAddress.street;
      this.flat = toSave.actualAddress.flat;
      this.house = toSave.actualAddress.house;
    } else if ("REG".equals(type)) {
      this.street = toSave.registrationAddress.street;
      this.flat = toSave.registrationAddress.flat;
      this.house = toSave.registrationAddress.house;
    }
    return this;
  }
}
