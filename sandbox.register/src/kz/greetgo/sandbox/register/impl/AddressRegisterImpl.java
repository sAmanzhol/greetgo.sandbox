package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.register.AddressRegister;
import kz.greetgo.sandbox.register.dao.AddressDao;

import java.util.List;

@Bean
public class AddressRegisterImpl implements AddressRegister {
    public BeanGetter<AddressDao> addressDao;

    @Override
    public List<Address> list() {
        return addressDao.get().list();
    }

    @Override
    public Long insert(Address address) {
        if(address == null)
            throw new NullPointerException("ADDRESS IS NULL");

        return addressDao.get().insert(address);
    }

    @Override
    public Address getById(Long id) {
        if(id == null)
            throw new NullPointerException("ID IS NULL");

        return addressDao.get().load(id);
    }

    @Override
    public List<Address> getByClientId(Long client_id) {
        if(client_id == null)
            throw  new NullPointerException("CLIENT_ID IS NULL");

        return addressDao.get().loadByClientId(client_id);
    }

    @Override
    public Address update(Address address) {
        if(address == null ||address.id == null)
            throw new NullPointerException("ID OR OBJECT IS NULL");

        return addressDao.get().update(address);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new NullPointerException("ID IS NULL");

        addressDao.get().delete(id);
    }

    @Override
    public void deleteByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException("CLIENT_ID IS NULL");

        addressDao.get().delete(client_id);
    }

}
