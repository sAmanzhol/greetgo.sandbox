package kz.greetgo.sandbox.controller.model;

import kz.greetgo.sandbox.controller.model.db.ClientPhoneDb;
import kz.greetgo.sandbox.controller.model.db.PhoneTypeDb;

import java.util.ArrayList;
import java.util.List;

public class Phone {
  public PhoneDetail detail;
  public String number;
  public String oldNumber;

  public Phone() {
  }

  public Phone(PhoneDetail detail, String number) {
    this.detail = detail;
    this.number = number;
    this.oldNumber = number;
  }

  public Phone(PhoneDetail detail, String number, String oldNumber) {
    this.detail = detail;
    this.number = number;
    this.oldNumber = oldNumber;
  }

  public static List<Phone> getPhoneListFromToSave(ClientToSave toSave) {

    List<Phone> phoneList = new ArrayList<>();
    for (Phone phone : toSave.phones) {
      if (phone == null || phone.number == null || phone.detail == null || PhoneTypeDb.parseOrNull(phone.detail.type.toString()) == null) {
        continue;
      }
      Phone ph = new Phone();
      ph.number = phone.number;
      ph.detail = new PhoneDetail(PhoneTypeDb.parseOrNull(phone.detail.type.toString()),
        PhoneTypeDb.parseOrNull(phone.detail.type.toString()).name);
      ph.oldNumber = phone.oldNumber;
      phoneList.add(ph);
    }
    return phoneList;
  }
  public static List<Phone> getPhoneListFromDb(List<ClientPhoneDb> phoneDbList) {
    List<Phone> phoneList = new ArrayList<>();
    for (ClientPhoneDb phone : phoneDbList) {
      if (phone == null || phone.number == null) {
        continue;
      }
      Phone ph = new Phone();
      ph.number = phone.number;
      ph.detail = new PhoneDetail(PhoneTypeDb.parseOrNull(phone.type),
        PhoneTypeDb.parseOrNull(phone.type).name);
      ph.oldNumber = phone.number;
      phoneList.add(ph);
    }
    return phoneList;
  }
}
