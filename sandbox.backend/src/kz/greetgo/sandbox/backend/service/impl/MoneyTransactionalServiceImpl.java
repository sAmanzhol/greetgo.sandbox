package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.service.MoneyService;
import kz.greetgo.sandbox.backend.service.MoneyTransactionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MoneyTransactionalServiceImpl implements MoneyTransactionalService {

  @Autowired
  private MoneyService moneyService;

  @Override
  @Transactional
  public void move(String idFrom, String idTo, long amount) {

    int cmp = idFrom.compareTo(idTo);
    if (cmp == 0) {
      return;
    }
    if (cmp > 0) {
      String tmp = idFrom;
      idFrom = idTo;
      idTo = tmp;
      amount = -amount;
    }

    long amountFrom = moneyService.getAmount(idFrom);
    long amountTo = moneyService.getAmount(idTo);

    amountFrom -= amount;
    amountTo += amount;

    moneyService.setAmount(idFrom, amountFrom);
    moneyService.setAmount(idTo, amountTo);
  }
}
