package kz.greetgo.sandbox.controller.model;



/**
 * Created by msultanova on 9/4/18.
 */
public class ClientRecord {
  public String fio;
  public String character;
  public int age;
  public int totalBalance;
  public int minBalance;
  public int maxBalance;

  //The following code would be not removed after regenerating
  ///LEAVE_FURTHER

  public ClientRecord(String fio, String character, int age, int totalBalance, int minBalance, int maxBalance) {
    this.fio = fio;
    this.character = character;
    this.age = age;
    this.totalBalance = totalBalance;
    this.minBalance = minBalance;
    this.maxBalance = maxBalance;
  }
}
