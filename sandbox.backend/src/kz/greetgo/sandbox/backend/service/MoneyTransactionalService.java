package kz.greetgo.sandbox.backend.service;

public interface MoneyTransactionalService {
  void move(String idFrom, String idTo, long amount);
}
