package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class ClientRecord {
  public String fio;
  public Character character;
  public int age;
  public float totalBalance;
  public float minBalance;
  public float maxBalance;
  public int clientId;

  public ClientRecord() {
  }

  public ClientRecord(String fio, Character character, int age, float totalBalance, float minBalance, float maxBalance, int clientId) {
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.totalBalance = totalBalance;
    this.minBalance = minBalance;
    this.maxBalance = maxBalance;
    this.clientId = clientId;
  }


  public ClientRecord convertToSaveToClientRecord(ClientToSave toSave){//}, TransactionInfo transactionInfo) {
    this.fio = toSave.surname + " " + toSave.name + " " + toSave.patronymic;
    LocalDate currentDate = LocalDate.now();
    LocalDate birthDate = toSave.birthDay.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    if ((birthDate != null) && (currentDate != null)) {
      this.age = Period.between(birthDate, currentDate).getYears();
    } else {
      this.age = 0;
    }
//    this.totalBalance = transactionInfo.totalBalance;
//    this.minBalance = transactionInfo.minBalance;
//    this.maxBalance = transactionInfo.maxBalance;
    this.character = toSave.character;
    this.clientId = toSave.clientID;
    return this;

  }
}
