package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.Exceptions.ExceptionStaticMessages;
import kz.greetgo.sandbox.controller.register.CharmRegister;
import kz.greetgo.sandbox.register.dao.CharmsDao;
import org.apache.log4j.Logger;

import java.util.List;

@Bean
public class CharmRegisterImpl implements CharmRegister {
    public BeanGetter<CharmsDao> charmsDao;

    final Logger logger = Logger.getLogger(getClass());

    @Override
    public List<Charm> list(){
        return charmsDao.get().list();
    }

    @Override
    public Charm getById(Long id) throws NullPointerException{
        if(id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Get Charm by id :  " + id);

        return charmsDao.get().load(id);
    }

    @Override
    public Long insert(Charm charm) {
        if(charm == null && charm.name != null)
            throw new NullPointerException("INSERTING CHARM IS NULL");

        if(logger.isInfoEnabled())
            logger.info("Insert Charm  :  " + charm);

        return charmsDao.get().insert(charm);
    }

    @Override
    public Charm update(Charm charm) {
        if(charm == null && charm.name != null && charm.id != null)
        throw new NullPointerException("UPDATING CHARM IS NULL");

        if(logger.isInfoEnabled())
            logger.info("Update Charm Values :  " + charm);

        return charmsDao.get().update(charm);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new NullPointerException("CHARM ID IS NULL");

        if(logger.isInfoEnabled())
            logger.info("Delete Charm by id :  " + id);

        charmsDao.get().delete(id);
    }


}
