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
            throw new NullPointerException("Id is null");

        return charmsDao.get().load(id);
    }

    @Override
    public Long insert(Charm charm) {
        if(charm == null)
            throw new NullPointerException("Inserting charms is null");

       return charmsDao.get().insert(charm);
    }


}
