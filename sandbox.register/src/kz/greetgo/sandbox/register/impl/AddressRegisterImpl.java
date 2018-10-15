package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Address;
import kz.greetgo.sandbox.controller.model.Exceptions.ExceptionStaticMessages;
import kz.greetgo.sandbox.controller.register.AddressRegister;
import kz.greetgo.sandbox.register.dao.AddressDao;
import org.apache.log4j.Logger;

import java.util.List;

@Bean
public class AddressRegisterImpl implements AddressRegister {
    public BeanGetter<AddressDao> addressDao;

    final Logger logger = Logger.getLogger(getClass());

    @Override
    public List<Address> list() {
        return addressDao.get().list();
    }

    @Override
    public Long insert(Address address) {
        if(address == null)
            throw new NullPointerException("ADDRESS IS NULL");

        if(logger.isInfoEnabled())
            logger.info("Insert Address by id : " + address);

        return addressDao.get().insert(address);
    }

    @Override
    public Address getById(Long id) {
        if(id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Get Address by id :" + id);

        return addressDao.get().load(id);
    }

    @Override
    public List<Address> getByClientId(Long client_id) {
        if(client_id == null)
            throw  new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Get Address by client_id :" + client_id);

        return addressDao.get().loadByClientId(client_id);
    }

    @Override
    public Address update(Address address) {
        if(address == null ||address.id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Update Address Values :  " + address);

        return addressDao.get().update(address);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Delete Address by id :  " + id);

        addressDao.get().delete(id);
    }

    @Override
    public void deleteByClientId(Long client_id) {
        if(client_id == null)
            throw new NullPointerException(ExceptionStaticMessages.ID_NULL);

        if(logger.isInfoEnabled())
            logger.info("Delete Address by client_id :  " + client_id);

        addressDao.get().delete(client_id);
    }

}
