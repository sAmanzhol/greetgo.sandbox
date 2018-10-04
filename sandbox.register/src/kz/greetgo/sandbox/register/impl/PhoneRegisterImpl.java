package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Phone;
import kz.greetgo.sandbox.controller.register.PhoneRegister;
import kz.greetgo.sandbox.register.dao.PhoneDao;

import java.util.List;

@Bean
public class PhoneRegisterImpl implements PhoneRegister {
    public BeanGetter<PhoneDao> phoneDao;

    @Override
    public List<Phone> list() {
        return phoneDao.get().list();
    }

    @Override
    public Long insert(Phone phone) {
        if(phone == null||phone.number == null||phone.clientId == null)
            throw new NullPointerException("INSERTING PHONE IS NULL");

       return phoneDao.get().insert(phone);
    }

    @Override
    public Phone update(Phone phone) {
        if(phone == null)
            throw new NullPointerException("UPDATING PHONE IS NULL");

        return phoneDao.get().update(phone);
    }

    @Override
    public Phone getById(Long id) {
        if(id == null)
            throw  new NullPointerException("PHONE ID IS NULL");

        return phoneDao.get().load(id);
    }

    @Override
    public List<Phone> getAllByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException("PHONE CLIENT_ID IS NULL");

        return phoneDao.get().loadByClientId(client_id);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw  new NullPointerException("PHONE ID IS NULL");

        phoneDao.get().delete(id);
    }

    @Override
    public void deleteByClientId(Long client_id) {
        if(client_id == null)
            throw  new NullPointerException("CLIENT_ID ID IS NULL");

        phoneDao.get().deleteByClientId(client_id);
    }
}
