package kz.greetgo.sandbox.register.dao_model.temp;

public class ClientAccountTransactionTemp {

  public String transactionType;
  public String accountNumber;
  public String finishedAt;
  public String money;
  public int status;

  public ClientAccountTransactionTemp() {
    transactionType = "";
    accountNumber = "";
    finishedAt = "";
    money = "";
    status = 0;
  }
}
