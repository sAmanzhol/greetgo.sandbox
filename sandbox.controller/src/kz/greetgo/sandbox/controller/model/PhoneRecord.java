package kz.greetgo.sandbox.controller.model;


// FIXME: 9/24/18 Название не корректное. Не понятно, что это тип номеров, а не сами номера
public class PhoneRecord {
  public String id;
  public String type;

  public PhoneRecord(String id, String type) {
    this.id = id;
    this.type = type;
  }
}
