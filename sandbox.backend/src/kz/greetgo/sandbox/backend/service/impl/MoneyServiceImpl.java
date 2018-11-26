package kz.greetgo.sandbox.backend.service.impl;

import kz.greetgo.sandbox.backend.dao.MoneyDao;
import kz.greetgo.sandbox.backend.service.MoneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoneyServiceImpl implements MoneyService {

  @Autowired
  private MoneyDao moneyDao;

  @Override
  public void create(String id, String room, long amount) {
    moneyDao.insert(id, room, amount);
  }

  @Override
  public long amountSum(String room) {
    return moneyDao.amountSum(room);
  }

  @Override
  public long getAmount(String id) {
    return moneyDao.getAmount(id);
  }

  @Override
  public void setAmount(String id, long amount) {
    moneyDao.setAmount(id, amount);
  }
}
