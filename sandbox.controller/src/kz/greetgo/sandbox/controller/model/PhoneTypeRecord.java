package kz.greetgo.sandbox.controller.model;


// FIXME: 9/24/18 Название не корректное. Не понятно, что это тип номеров, а не сами номера
public class PhoneTypeRecord {
  public String type;

  public PhoneTypeRecord(Object o) {
    this.type = o.toString();
  }
}
