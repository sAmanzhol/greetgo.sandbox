package kz.greetgo.sandbox.controller.model;


// FIXME: 9/24/18 Название не корректное. Не понятно, что это тип номеров, а не сами номера
public class PhoneTypeRecord {
  public int id;
  public String type;

  public PhoneTypeRecord(int id, String type) {
    this.id = id;
    this.type = type;
  }
}
