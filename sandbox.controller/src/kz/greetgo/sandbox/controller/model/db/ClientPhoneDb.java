package kz.greetgo.sandbox.controller.model.db;

import kz.greetgo.sandbox.controller.model.ClientToSave;
import kz.greetgo.sandbox.controller.model.Phone;

import java.util.ArrayList;
import java.util.List;

public class ClientPhoneDb {
  public int client;
  public String number;
  public PhoneTypeDb type;
  public String oldNumber;
  public boolean actual;

  public static List<ClientPhoneDb> getPhoneListFromToSave(ClientToSave toSave) {

    List<ClientPhoneDb> phoneList = new ArrayList<>();
    for (Phone phone : toSave.phones) {
      ClientPhoneDb phoneDb = new ClientPhoneDb();
      phoneDb.client = toSave.clientID;
      phoneDb.number = phone.number;
      phoneDb.oldNumber = phone.oldNumber;
      phoneDb.type = PhoneTypeDb.parseOrNull(phone.detail.type.toString());
      phoneList.add(phoneDb);
    }
    return phoneList;
  }
}
