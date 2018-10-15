package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Exceptions.ExceptionStaticMessages;
import kz.greetgo.sandbox.controller.model.Phone;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.dao.PhoneDao;
import org.apache.log4j.Logger;

import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {
    public BeanGetter<PhoneDao> phoneDao;

    final Logger logger = Logger.getLogger(getClass());

    @Override
    public List<Phone> list() {
        List<Phone> phoneList = phoneDao.get().list();

        if (logger.isInfoEnabled())
            logger.info("Get all Phones :  " + phoneList);

        return phoneList;
    }

    @Override
    public Long insert(Phone phone) {
        if(phone == null||phone.number == null||phone.client == null)
            throw new NullPointerException("INSERTING PHONE IS NULL");

        if (logger.isInfoEnabled())
            logger.info("Insert Phone Values :  " + phone);

        return phoneDao.get().insert(phone);
    }

    @Override
    public Phone update(Phone phone) {
        if(phone == null)
            throw new NullPointerException("UPDATING PHONE IS NULL");

        if (logger.isInfoEnabled())
            logger.info("Update Phone Values :  " + phone);

        return phoneDao.get().update(phone);
    }

    @Override
    public Phone getById(Long id) {
        if(id == null)
            throw  new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if (logger.isInfoEnabled())
            logger.info("Get Phone by id :  " + id);

        return phoneDao.get().load(id);
    }

    @Override
    public List<Phone> getAllByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if (logger.isInfoEnabled())
            logger.info("Get Phone list of client by id :  " + client_id);

        return phoneDao.get().loadByClientId(client_id);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw  new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if (logger.isInfoEnabled())
            logger.info("Delete Phone  by id :  " + id);

        phoneDao.get().delete(id);
    }

    @Override
    public void deleteByClientId(Long client_id) {
        if(client_id == null)
            throw  new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if (logger.isInfoEnabled())
            logger.info("Delete Phone  by client_id :  " + client_id);

        phoneDao.get().deleteByClientId(client_id);
    }
}
