package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Account;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.AccountRegister;
import kz.greetgo.sandbox.register.dao.AccountDao;

import java.util.List;

@Bean
public class AccountRegisterImpl implements AccountRegister {
    public BeanGetter<AccountDao> accountDao;


    @Override
    public Long insert(Account account) {
        if(hasNull(account))
            throw new NullPointerException("NULL VALUES IN ACCOUNT OBJECT");

        return accountDao.get().insert(account);
    }

    @Override
    public Account getById(Long id) {
        if(id == null)
            throw new NullPointerException("ACCOUNT ID IS NULL");

        return accountDao.get().getById(id);
    }

    @Override
    public List<Account> getByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException("CLIENT ID IS NULL");

        return accountDao.get().getByClientId(client_id);
    }

    @Override
    public Account update(Account account) {
        if(hasNull(account) && account.id == null)
            throw new NullPointerException("NULL VALUES IN ACCOUNT OBJECT");

        return accountDao.get().update(account);

    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new NullPointerException("ACCOUNT ID IS NULL");

        accountDao.get().delete(id);
    }

    @Override
    public void deleteByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException("CLIENT ID IS NULL");

        accountDao.get().deleteByClientId(client_id);
    }


    boolean hasNull(Account account) {
        return account == null
                || account.money == null
                || account.number == null
                || account.registered_at == null
                || account.actual == null;
    }
}
