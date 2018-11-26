package kz.greetgo.sandbox.backend.service;

public interface MoneyService {
  void create(String id, String room, long amount);

  long amountSum(String room);

  long getAmount(String id);

  void setAmount(String id, long amount);
}
