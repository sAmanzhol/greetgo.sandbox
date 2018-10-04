package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.CharmRegister;
import kz.greetgo.sandbox.register.dao.CharmsDao;

import java.util.List;

@Bean
public class CharmRegisterImpl implements CharmRegister {

    public BeanGetter<CharmsDao> charmsDao;

    @Override
    public List<Charm> list(){
        return charmsDao.get().list();
    }

    @Override
    public Charm getById(Long id) throws NullPointerException{
        if(id == null)
            throw new NullPointerException("CHARM ID IS NULL");

        return charmsDao.get().load(id);
    }

    @Override
    public Long insert(Charm charm) {
        if(charm == null && charm.name != null)
            throw new NullPointerException("INSERTING CHARM IS NULL");

       return charmsDao.get().insert(charm);
    }

    @Override
    public Charm update(Charm charm) {
        if(charm == null && charm.name != null && charm.id != null)
        throw new NullPointerException("UPDATING CHARM IS NULL");

        return charmsDao.get().update(charm);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new NullPointerException("CHARM ID IS NULL");

        charmsDao.get().delete(id);
    }


}
